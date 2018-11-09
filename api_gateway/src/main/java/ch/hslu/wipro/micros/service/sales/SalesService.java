package ch.hslu.wipro.micros.service.sales;

import ch.hslu.wipro.micros.rabbit.RabbitMqClient;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Path("/sales")
public class SalesService {

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
            client.call(command, "ch.hslu.wipro.micros.Order"); //Todo From discovery
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(201).entity("order created - customer.id: "+orderDTO.getCustomerId()).build();
    }
}
