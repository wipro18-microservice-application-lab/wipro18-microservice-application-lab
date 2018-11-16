package main

import (
	"github.com/streadway/amqp"
	"log"
	"net/smtp"
)

var (
	queueName = "ch.hslu.wipro.micros.Confirmation"
	exchangeName = "ch.hslu.wipro.micros.Order"
	host = "rabbitmq"
)

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
	}
}

func declareQueue(ch *amqp.Channel, queueName string) {
	_, err := ch.QueueDeclare(
		queueName,
		false, 		// durable
		false, 	// delete when unused
		true,  		// exclusive
		false, 		// no-wait
		nil,   		// arguments
	)

	failOnError(err, "Failed to declare a queue")
}

func bindQueue(ch *amqp.Channel, queueName string, exchangeName string) {
	ch.QueueBind(
		queueName,
		"order.event.complete",
		exchangeName,
		false,
		nil,
	)
}

func listenForOrderComplete(ch *amqp.Channel, queueName string) {
	confirmations, err := ch.Consume(
		queueName,
		"",    	// consumer
		false, 		// auto-ack
		false, 		// exclusive
		false, 		// no-local
		false, 		// no-wait
		nil,   		// args
	)
	failOnError(err, "Failed to register a consumer")

	forever := make(chan bool)

	go func() {
		for d := range confirmations {
			send(string(d.Body))

			d.Ack(false)
		}
	}()

	log.Printf(" [*] Awaiting RPC requests")
	<-forever
}

func send(body string) {
	// Birthday 23. April 1993
	from := "noreply.wipro18@gmail.com"
	pass := "wipro18-applab"
	to := "alan.meile@gmail.com"

	msg := 	"From: " + from + "\n" +
			"To: " + to + "\n" +
			"Subject: Hello there\n\n" +
			body

	err := smtp.SendMail("smtp.gmail.com:587",
		smtp.PlainAuth("", from, pass, "smtp.gmail.com"),
		from, []string{to}, []byte(msg))

	if err != nil {
		log.Printf("smtp error: %s", err)
		return
	}

	log.Print("confirmation email sent")
}

func main() {
	conn, err := amqp.Dial("amqp://guest:guest@" + host + ":5672/")
	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()

	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer ch.Close()

	declareQueue(ch, queueName)
	bindQueue(ch, queueName, exchangeName)

	listenForOrderComplete(ch, queueName)
}