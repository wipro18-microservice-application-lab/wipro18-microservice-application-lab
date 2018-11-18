package ch.hslu.wipro.micros.service;

import org.junit.Assert;
import org.junit.Test;

public class MessageDomainTest {

    @Test
    public void testGetCommand() {
        MessageDomain domain = new MessageDomain("test");
        domain.addCommand("order.command.test");
        Assert.assertEquals("order.command.test", domain.getCommand("order"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCommandException() {
        MessageDomain domain = new MessageDomain("test");
        domain.getCommand("test");
    }
}
