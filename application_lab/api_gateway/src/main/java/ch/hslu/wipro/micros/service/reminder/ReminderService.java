package ch.hslu.wipro.micros.service.reminder;

import ch.hslu.wipro.micros.rabbit.Command;
import ch.hslu.wipro.micros.rabbit.MessageBroker;
import ch.hslu.wipro.micros.rabbit.MessageBrokerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("reminders")
public class ReminderService {
    private static Logger LOGGER = LogManager.getLogger();

    @GET
    @Path("health")
    @Produces(MediaType.TEXT_PLAIN)
    public String healthCheck() {
        Response response = getAllReminders();
        String healthText = "Not running";
        if (response.getStatus() == Response.ok().build().getStatus()) {
            healthText = "running";
        }
        return healthText;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReminders() {
        Command<String> command = ReminderCommandFactory.createGetAllRemindersCommand();
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
