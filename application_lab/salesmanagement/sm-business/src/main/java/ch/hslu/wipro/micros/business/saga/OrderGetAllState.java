package ch.hslu.wipro.micros.business.saga;

import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.service.repository.RepositoryFactory;
import ch.hslu.wipro.micros.service.repository.RepositoryService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OrderGetAllState implements OrderSagaState {

    @Override
    public void process(OrderSaga saga) throws IOException {
        Channel channel = saga.getContext().getChannel();
        String correlationId = saga.getContext().getCorrelationId();
        String replyToQueue = saga.getContext().getReplyToQueue();
        String replyRoutingKey = "";

        RepositoryService repositoryService = RepositoryFactory.getRepository();
        List<OrderDto> orders = repositoryService.getAllOrders();

        String jsonOrders = saga.getContext().getJsonConverter().toJson(orders);

        AMQP.BasicProperties replyProperties = new AMQP.BasicProperties
                .Builder()
                .correlationId(correlationId)
                .build();

        channel.basicPublish(
                replyRoutingKey,
                replyToQueue,
                replyProperties,
                jsonOrders.getBytes(StandardCharsets.UTF_8));

        boolean acknowledgeAll = false;
        channel.basicAck(saga.getContext().getDeliveryTag(), acknowledgeAll);
    }
}