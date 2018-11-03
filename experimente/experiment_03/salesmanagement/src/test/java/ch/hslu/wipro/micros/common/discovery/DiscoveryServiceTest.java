package ch.hslu.wipro.micros.common.discovery;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class DiscoveryServiceTest {
    private static final String ORDER_EXCHANGE_CONNECTION = "ch.hslu.wipro.micros.Order";
    private static final String COMMAND_QUEUE_CONNECTION = "ch.hslu.wipro.micros.OrderCommand";
    private static final String EVENT_QUEUE_CONNECTION = "ch.hslu.wipro.micros.OrderEvent";
    private DiscoveryService discoveryService;

    @Before
    public void setUp() throws Exception {
        String discoveryJsonResponse = "{\n" +
                "  \"exchange\": \"ch.hslu.wipro.micros.Order\", \n" +
                "  \"queues\": [\n" +
                "    \"ch.hslu.wipro.micros.OrderCommand\", \n" +
                "    \"ch.hslu.wipro.micros.OrderEvent\", \n" +
                "    \"ch.hslu.wipro.micros.Audit\"\n" +
                "  ]\n" +
                "}";

        discoveryService = new DiscoveryService(StrategyFactory.discoverByUrl(), DomainType.ORDER);
    }

    @Test
    public void testGetExchangeConnection() {
        assertEquals(ORDER_EXCHANGE_CONNECTION, discoveryService.getExchange());
    }

    @Test
    public void getUnknownQueueConnection() {
        assertEquals(Optional.empty(), discoveryService.getQueueForSubject("UNKNOWN"));
    }

    @Test
    public void getCommandQueueConnection() {
        assertEquals(Optional.of(COMMAND_QUEUE_CONNECTION), discoveryService.getQueueForSubject(QueueType.COMMAND));
    }

    @Test
    public void getEventQueueConnection() {
        assertEquals(Optional.of(EVENT_QUEUE_CONNECTION), discoveryService.getQueueForSubject(QueueType.EVENT));
    }
}