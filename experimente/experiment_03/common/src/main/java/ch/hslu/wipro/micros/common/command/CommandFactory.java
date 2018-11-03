package ch.hslu.wipro.micros.common.command;

import ch.hslu.wipro.micros.common.dto.OrderDto;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class CommandFactory {

    public static String getCheckOrderCommandAsJson(OrderDto orderDto) {
        return new Gson().toJson(new CheckOrderCommand(orderDto));
    }

    public static Optional<Command> getCommandFromBytes(byte[] body) {
        String bodyUtf8 = new String(body, StandardCharsets.UTF_8);
        Gson gson = new Gson();

        if (bodyUtf8.contains(CheckOrderCommand.class.getName())) {
            return Optional.of(gson.fromJson(bodyUtf8, CheckOrderCommand.class));
        }

        return Optional.empty();
    }
}
