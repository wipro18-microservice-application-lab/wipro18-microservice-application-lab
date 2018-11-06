package ch.hslu.wipro.micros.common.event;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class EventFactory {

    public static String getOrderConfirmedEventAsJson(String attendant) {
        return new Gson().toJson(new OrderConfirmedEvent(attendant));
    }

    public static Optional<Event> getEventFromBytes(byte[] body) {
        String bodyUtf8 = new String(body, StandardCharsets.UTF_8);
        Gson gson = new Gson();

        if (bodyUtf8.contains(OrderConfirmedEvent.class.getName())) {
            return Optional.of(gson.fromJson(bodyUtf8, OrderConfirmedEvent.class));
        }

        if (bodyUtf8.contains(OrderCompleteEvent.class.getName())) {
            return Optional.of(gson.fromJson(bodyUtf8, OrderCompleteEvent.class));
        }

        return Optional.empty();
    }

    public static String getOrderCompleteAsJson() {
        return new Gson().toJson(new OrderCompleteEvent());
    }
}
