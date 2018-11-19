package ch.hslu.wipro.micros.service.config;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ConfigServiceTest {
    private String[] routingTopics;
    private String ORDER_COMMAND_CREATE = "order.command.create";

    @Before
    public void setUp() throws Exception {
        String properties = new StringBuilder()
                .append("order.command.create;")
                .append("order.command.getAll;")
                .append("order.command.getAllByCustomerId;")
                .append("order.command.updateStatus")
                .toString();

        routingTopics = properties.split(";");
    }

    @Test
    public void getRoutingKeyForCreate() {
        String topic = "create";

        List<String> result = Arrays.stream(routingTopics)
                .filter(i -> i.contains(topic))
                .collect(Collectors.toList());

        assertEquals(ORDER_COMMAND_CREATE, result.get(0));
    }
}