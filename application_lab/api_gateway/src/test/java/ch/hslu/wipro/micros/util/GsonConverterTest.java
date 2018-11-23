package ch.hslu.wipro.micros.util;

import ch.hslu.wipro.micros.service.sales.dtos.OrderDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link GsonConverter}
 */
public class GsonConverterTest {

    private JsonConverter orderJsonConverter;

    @Before
    public void before() {
        orderJsonConverter = new GsonConverter();
    }

    @Test
    public void testConverting() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTotalPrice(1000.0);
        orderDTO.setCustomerId(22);
        String result = orderJsonConverter.toJsonString(orderDTO);
        Assert.assertEquals("{\"customerId\":22,\"totalPrice\":1000.0}", result);
    }
}
