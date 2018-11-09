package ch.hslu.wipro.micros.service.convertion;

import ch.hslu.wipro.micros.model.order.OrderDto;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

public class OrderCreateCommandConverter {
    private static final Gson jsonConverter = new Gson();

    public static OrderDto fromBytes(byte[] body) {
        String jsonOrderDto = new String(body, StandardCharsets.UTF_8);

        return jsonConverter.fromJson(jsonOrderDto, OrderDto.class);
    }
}