package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.service.convertion.JsonConverter;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

class OrderSagaContext {
    private OrderDto order;
    private AMQP.BasicProperties properties;
    private Channel channel;
    private JsonConverter jsonConverter;
    private long deliveryTag;

    void setOrder(OrderDto order) {
        this.order = order;
    }

    void setChannel(Channel channel) {
        this.channel = channel;
    }

    void setProperties(AMQP.BasicProperties properties) {
        this.properties = properties;
    }

    void setDeliveryTag(long deliveryTag) {
        this.deliveryTag = deliveryTag;
    }

    void setJsonConverter(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    OrderDto getOrder() {
        return order;
    }

    Channel getChannel() {
        return channel;
    }

    String getCorrelationId() {
        return properties.getCorrelationId();
    }

    String getReplyToQueue() {
        return properties.getReplyTo();
    }

    long getDeliveryTag() {
        return deliveryTag;
    }

    public JsonConverter getJsonConverter() {
        return jsonConverter;
    }
}