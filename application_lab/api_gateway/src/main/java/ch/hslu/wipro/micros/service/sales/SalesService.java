package ch.hslu.wipro.micros.service.sales;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.rabbit.MessageBroker;
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
    public Response createOrder(OrderDTO orderDTO) {
        System.out.println(orderDTO.getAmountToArticle());

        Command<OrderDTO> command = CommandFactory.createOrderCreateCommand(orderDTO);
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders() {
        Command<String> command = CommandFactory.createGetAllOrdersCommand();
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    @Path("customer/{customer}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getAllOrdersByCustomerId(@PathParam("customer") long id) {
        Command<CustomerIdDTO> command = CommandFactory.createGetAllByCustomerIdCommand(null);
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    public Response updateOrderStatus(UpdateOrderDTO dto) {
        Command<UpdateOrderDTO> command = CommandFactory.createUpdateOrderStatusCommand(dto);
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    private String callMessageBroker(Command<?> command) {
        String answer = null;
        try {
            MessageBroker client = new RabbitClient();
            answer = client.call(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(answer).orElse("{error}");
    }
}
