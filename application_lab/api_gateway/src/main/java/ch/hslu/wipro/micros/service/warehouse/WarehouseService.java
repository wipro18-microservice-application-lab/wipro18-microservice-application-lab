package ch.hslu.wipro.micros.service.warehouse;

import ch.hslu.wipro.micros.rabbit.RabbitMqClient;
import ch.hslu.wipro.micros.service.sales.CommandFactory;
import ch.hslu.wipro.micros.service.sales.OrderDTO;
import ch.hslu.wipro.micros.service.sales.SalesCommand;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/warehouse")
public class WarehouseService {

    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "running";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(OrderDTO orderDTO) {
        SalesCommand command = CommandFactory.createCreateOrderCommand(orderDTO); // Todo Interfaces

        try {
            RabbitMqClient client = new RabbitMqClient();
            client.call(command, "ch.hslu.wipro.micros.Article"); //Todo From discovery
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(201).entity("[{\"id\":\"ec7445cb-44f0-4f6d-95d1-295c9a506f40\",\"name\":\"Samsung Galaxy A6\",\"description\":\"Smartphone\",\"price\":500.0}]\n").build();
    }
}
