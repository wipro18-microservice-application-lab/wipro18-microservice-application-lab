package ch.hslu.wipro.micros.service.warehouse;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.rabbit.MessageBroker;
import ch.hslu.wipro.micros.rabbit.MessageBrokerFactory;
import ch.hslu.wipro.micros.rabbit.RabbitClient;
import ch.hslu.wipro.micros.service.warehouse.dtos.ArticleDTO;
import ch.hslu.wipro.micros.service.warehouse.dtos.ArticleIdDTO;
import ch.hslu.wipro.micros.service.warehouse.dtos.CheckQuantityDTO;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("warehouse")
public class WarehouseService {
    private static Logger LOGGER = LogManager.getLogger();

    @GET
    @Path("health")
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "running";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllArticles() {
        Command<String> command = ArticleCommandFactory.createGetAllArticleCommand();
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    @Path("articles/{article}")
    @GET
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getById(@PathParam("article") long id) {
        LOGGER.info("get article by article id " + id);
        ArticleIdDTO dto = new ArticleIdDTO();
        dto.setArticleId(id);
        Command<ArticleIdDTO> command = ArticleCommandFactory.createGetByIdCommand(dto);
        String answer = callMessageBroker(command);
        return Response.ok(answer, MediaType.APPLICATION_JSON).build();
    }

    @Path("quantity")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkQuantity(CheckQuantityDTO dto) {
        Command<CheckQuantityDTO> command = ArticleCommandFactory.createCheckQuantityCommand(dto);
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
