package ch.hslu.wipro.micros.service.warehouse;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.rabbit.RabbitClient;
import ch.hslu.wipro.micros.rabbit.RabbitMqClient;
import ch.hslu.wipro.micros.service.CommandFactory;
import ch.hslu.wipro.micros.service.sales.OrderDTO;
import ch.hslu.wipro.micros.service.sales.SalesCommand;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/warehouse")
public class WarehouseService {

    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        return "running";
    }

    @GET
    @Path("allArticles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllArticles() {
        Command command = CommandFactory.createGetAllArticleCommand();
        String rabbitAnswer = null;
        try {
            RabbitClient client = new RabbitClient();
            //rabbitAnswer = client.call(command, "ch.hslu.wipro.micros.Article"); //Todo make rabbit call
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return Response.status(201).entity("[{\"id\":\"ec7445cb-44f0-4f6d-95d1-295c9a506f40\",\"name\":\"Samsung Galaxy A6\",\"description\":\"Smartphone\",\"price\":500.0}]\n").build();
        return Response.ok(generatePseudoData(), MediaType.APPLICATION_JSON).build(); // Todo replace with rabbits answer
    }

    /**
     * TO DELETE
     * Generates pseudo data for rest testing.
     * @return
     */
    private String generatePseudoData() {
        List<ArticleDTO> articles = new ArrayList<>();
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId("1112a");
        articleDTO.setName("Tablet");
        articleDTO.setDescription("A tablet.");
        articleDTO.setPrice(550.0);
        articles.add(articleDTO);

        ArticleDTO articleDTO2 = new ArticleDTO();
        articleDTO2.setId("1152a");
        articleDTO2.setName("Pen");
        articleDTO2.setDescription("A pen.");
        articleDTO2.setPrice(1250.0);
        articles.add(articleDTO2);

        return new Gson().toJson(articles);
    }
}
