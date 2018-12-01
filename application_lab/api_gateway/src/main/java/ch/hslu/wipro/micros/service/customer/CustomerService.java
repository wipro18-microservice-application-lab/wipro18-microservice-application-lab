package ch.hslu.wipro.micros.service.customer;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.rabbit.MessageBroker;
import ch.hslu.wipro.micros.rabbit.MessageBrokerFactory;
import ch.hslu.wipro.micros.service.customer.dto.CustomerCreateDTO;
import ch.hslu.wipro.micros.service.customer.dto.CustomerByIdDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("customers")
public class CustomerService {
    private static Logger LOGGER = LogManager.getLogger();

    @GET
    @Path("health")
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "running";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders() {
        Command<String> command = CustomerCommandFactory.createGetAllCustomersCommand();
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    @Path("/{customer}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getById(@PathParam("customer") long id) {
        LOGGER.info("get customer by customer id " + id);
        CustomerByIdDTO dto = new CustomerByIdDTO();
        dto.setCustomerId(id);
        Command<CustomerByIdDTO> command = CustomerCommandFactory.createGetByCustomerIdCommand(dto);
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    @Path("reminders/{customer}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getReminderById(@PathParam("customer") long id) {
        LOGGER.info("get reminder by customer id " + id);
        CustomerByIdDTO dto = new CustomerByIdDTO();
        dto.setCustomerId(id);
        Command<CustomerByIdDTO> command = CustomerCommandFactory.createGetReminderByIdCommand(dto);
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(CustomerCreateDTO dto) {
        LOGGER.info("create customer: fullname: "+dto.getFullName());
        Command<CustomerCreateDTO> command = CustomerCommandFactory.createCreateCustomerCommand(dto);
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
