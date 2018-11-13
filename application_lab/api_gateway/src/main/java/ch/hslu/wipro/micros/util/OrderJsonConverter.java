package ch.hslu.wipro.micros.util;

import ch.hslu.wipro.micros.service.sales.OrderDTO;
import com.google.gson.Gson;

/**
 * This class represents a json converter for OrderDTOs.
 */
public class OrderJsonConverter implements JsonConverter<OrderDTO> {

    @Override
    public String toJsonString(OrderDTO data) {
        return new Gson().toJson(data);
    }
}
