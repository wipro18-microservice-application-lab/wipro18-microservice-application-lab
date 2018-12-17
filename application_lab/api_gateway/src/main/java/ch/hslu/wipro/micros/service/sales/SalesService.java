package ch.hslu.wipro.micros.service.sales;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.rabbit.MessageBroker;
import ch.hslu.wipro.micros.rabbit.MessageBrokerFactory;
import ch.hslu.wipro.micros.service.sales.dtos.CustomerIdDTO;
import ch.hslu.wipro.micros.service.sales.dtos.OrderDTO;
import ch.hslu.wipro.micros.service.sales.dtos.UpdateOrderDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("sales")
public class SalesService {
    private static Logger LOGGER = LogManager.getLogger();

    @GET
    @Path("health")
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        Response response = getAllOrders();
        String healthText = "Not running";
        if (response.getStatus() == Response.ok().build().getStatus()) {
            healthText = "running";
        }
        return healthText;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(OrderDTO orderDTO) {
        LOGGER.info("create order: customer: "+orderDTO.getCustomerId()+", articles: "+orderDTO.getAmountToArticle());
        Command<OrderDTO> command = OrderCommandFactory.createOrderCreateCommand(orderDTO);
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders() {
        Command<String> command = OrderCommandFactory.createGetAllOrdersCommand();
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    @Path("customer/{customer}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getAllOrdersByCustomerId(@PathParam("customer") long id) {
        LOGGER.info("get all orders by customer id " + id);
        CustomerIdDTO dto = new CustomerIdDTO();
        dto.setCustomerId(id);
        Command<CustomerIdDTO> command = OrderCommandFactory.createGetAllByCustomerIdCommand(dto);
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrderStatus(UpdateOrderDTO dto) {
        Command<UpdateOrderDTO> command = OrderCommandFactory.createUpdateOrderStatusCommand(dto);
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    private String callMessageBroker(Command<?> command) {
        String answer = null;
        try {
            MessageBroker client = MessageBrokerFactory.createMessageBrokerClient();
            answer = client.call(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(answer).orElse("{error}");
    }
}
