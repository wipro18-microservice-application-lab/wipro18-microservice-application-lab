package ch.hslu.wipro.micros.business.rabbitmq.consumer;

import ch.hslu.wipro.micros.business.converter.JsonConverter;
import ch.hslu.wipro.micros.business.converter.JsonConverterFactory;
import ch.hslu.wipro.micros.business.rabbitmq.command.Command;
import ch.hslu.wipro.micros.business.rabbitmq.command.CommandBuilder;
import ch.hslu.wipro.micros.business.saga.OrderCreateState;
import ch.hslu.wipro.micros.business.saga.OrderSaga;
import ch.hslu.wipro.micros.business.saga.OrderSagaContext;
import ch.hslu.wipro.micros.business.saga.OrderSagaContextBuilder;
import ch.hslu.wipro.micros.model.order.OrderDto;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.rabbitmq.client.AMQP.BasicProperties;

@SuppressWarnings("unchecked")
public class OrderCreateCommandConsumer extends DefaultConsumer {
    private static final Logger logger = LogManager.getLogger(OrderCreateCommandConsumer.class);

    public OrderCreateCommandConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               BasicProperties properties, byte[] body) {

        logger.info("handle incoming OrderCreateCommand with correlation id: {}", properties.getCorrelationId());

        JsonConverter<OrderDto> jsonConverter = new JsonConverterFactory<OrderDto>().get();
        OrderDto orderDto = jsonConverter.fromJson(body, OrderDto.class);

        Command<OrderDto> orderCreateCommand = new CommandBuilder<OrderDto>()
                .atDeliveryTag(envelope.getDeliveryTag())
                .withPayload(orderDto)
                .atCorrelationId(properties.getCorrelationId())
                .atReplyTo(properties.getReplyTo())
                .build();

        OrderSagaContext orderSagaContext = new OrderSagaContextBuilder()
                .overChannel(super.getChannel())
                .atCommand(orderCreateCommand)
                .build();

        OrderSaga orderSaga = new OrderSaga(orderSagaContext, new OrderCreateState());
        orderSaga.process();
    }
}