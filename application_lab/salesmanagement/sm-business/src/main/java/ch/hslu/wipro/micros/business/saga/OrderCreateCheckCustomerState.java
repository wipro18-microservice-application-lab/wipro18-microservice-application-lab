package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.business.discovery.DiscoveryService;
import ch.hslu.wipro.micros.business.discovery.DiscoveryServiceFactory;
import ch.hslu.wipro.micros.business.discovery.MicroService;
import ch.hslu.wipro.micros.business.rabbitmq.consumer.CustomerCheckReplyConsumer;
import ch.hslu.wipro.micros.model.customer.CustomerDto;
import ch.hslu.wipro.micros.model.order.OrderDto;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class OrderCreateCheckCustomerState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) throws IOException {
        Channel channel = saga.getContext().getChannel();
        String correlationId = UUID.randomUUID().toString();
        String replyToQueue = channel.queueDeclare().getQueue();

        OrderDto orderDto = saga.getContext().getCommand().getPayload();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(orderDto.getCustomerId());

        String customerByIdJson = new JsonConverterFactory<CustomerDto>().get()
                .toJson(customerDto);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(correlationId)
                .replyTo(replyToQueue)
                .build();

        DiscoveryService discoveryService = new DiscoveryServiceFactory().get();
        MicroService customerService = discoveryService.getCustomerManagement();

        channel.basicPublish(
                customerService.getExchange(),
                customerService.getCommands("getById"),
                replyProperties,
                customerByIdJson.getBytes(StandardCharsets.UTF_8));

        boolean noAutoAck = true;
        channel.basicConsume(replyToQueue, noAutoAck, new CustomerCheckReplyConsumer(channel, saga));
    }
}
