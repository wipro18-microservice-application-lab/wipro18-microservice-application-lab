package ch.hslu.wipro.micros.util;

import ch.hslu.wipro.micros.service.sales.OrderDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link OrderJsonConverter}
 */
public class OrderJsonConverterTest {

    JsonConverter<OrderDTO> orderDTOJsonConverter;

    @Before
    public void before() {
        orderDTOJsonConverter = new OrderJsonConverter();
    }

    @Test
    public void testConverting() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTotalPrice(1000.0);
        orderDTO.setCustomerId(22);
        String result = orderDTOJsonConverter.toJsonString(orderDTO);
        Assert.assertEquals("{\"customerId\":22,\"totalPrice\":1000.0}", result);
    }
}
