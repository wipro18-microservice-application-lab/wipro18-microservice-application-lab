package ch.hslu.wipro.micros.service.sales;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.rabbit.RabbitClient;
import ch.hslu.wipro.micros.service.CommandFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("sales")
public class SalesService {

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
        Command<OrderDTO> command = CommandFactory.createOrderCreateCommand(orderDTO);
        String rabbitAnswer = null;
        try {
            RabbitClient client = new RabbitClient();
            rabbitAnswer = client.call(command, "ch.hslu.wipro.micros.Order"); //Todo From discovery
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jsonString = Optional.ofNullable(rabbitAnswer).orElse("{error}");

        return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
    }
}
