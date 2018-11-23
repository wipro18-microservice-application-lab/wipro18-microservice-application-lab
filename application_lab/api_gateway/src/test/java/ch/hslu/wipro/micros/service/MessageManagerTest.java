package ch.hslu.wipro.micros.service;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is a test class for {@link ch.hslu.wipro.micros.service.MessageManager}
 */
public class MessageManagerTest {

    @Test
    public void testGetExchange() {
        MessageManager manager = new MessageManagerFake();
        manager.prepareMessageDomain();
        Assert.assertEquals("fake.exchange", manager.getExchange());
    }

    @Test
    public void testGetCommandKey() {
        MessageManager manager = new MessageManagerFake();
        manager.prepareMessageDomain();
        Assert.assertNotNull(manager.getCommandKey("fake"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCommandKeyException() {
        MessageManager manager = new MessageManagerFake();
        manager.prepareMessageDomain();
        Assert.assertNotNull(manager.getCommandKey("missing"));
    }
}
