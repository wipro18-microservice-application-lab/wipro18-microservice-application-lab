package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.service.convertion.JsonConverter;
import com.rabbitmq.client.Channel;

import static com.rabbitmq.client.AMQP.BasicProperties;

public class OrderSagaBuilder {
    private JsonConverter jsonConverter;
    private BasicProperties properties;
    private OrderSagaContext context;
    private OrderSagaState state;
    private long deliveryTag;
    private Channel channel;

    public OrderSagaBuilder() {
        this.context = new OrderSagaContext();
    }

    public OrderSagaBuilder withJsonConverter(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
        return this;
    }

    public OrderSagaBuilder withStateSequence(OrderSagaState state) {
        this.state = state;
        return this;
    }

    public OrderSagaBuilder withProperties(BasicProperties properties) {
        this.properties = properties;
        return this;
    }

    public OrderSagaBuilder atOrder(OrderDto order) {
        context.setOrder(order);
        return this;
    }

    public OrderSagaBuilder overChannel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public OrderSagaBuilder atDeliveryTag(long deliveryTag) {
        this.deliveryTag = deliveryTag;
        return this;
    }

    public OrderSaga build() {
        context.setJsonConverter(jsonConverter);
        context.setChannel(channel);
        context.setProperties(properties);
        context.setDeliveryTag(deliveryTag);
        OrderSaga orderSaga = new OrderSaga(context);
        orderSaga.setState(state);

        return orderSaga;
    }
}
