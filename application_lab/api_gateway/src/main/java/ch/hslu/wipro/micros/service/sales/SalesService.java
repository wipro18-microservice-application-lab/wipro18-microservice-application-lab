package ch.hslu.wipro.micros.service.sales;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.rabbit.MessageBroker;
import ch.hslu.wipro.micros.rabbit.RabbitClient;
import ch.hslu.wipro.micros.service.CommandFactory;
import ch.hslu.wipro.micros.service.MessageDomain;
import ch.hslu.wipro.micros.service.MessageRepository;
import ch.hslu.wipro.micros.service.StaticMessageRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("sales")
public class SalesService {
    private static MessageRepository REPOSITORY = StaticMessageRepository.getMessageRepository();

    @GET
    @Path("health")
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "running";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(OrderDTO orderDTO) {
        MessageDomain domain = REPOSITORY.getDomain("order");
        Command<OrderDTO> command = CommandFactory.createOrderCreateCommand(orderDTO);
        String rabbitAnswer = null;
        try {
            MessageBroker client = new RabbitClient();
            rabbitAnswer = client.call(command, domain.getExchange());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jsonString = Optional.ofNullable(rabbitAnswer).orElse("{error}");

        return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
    }
}
