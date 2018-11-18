package ch.hslu.wipro.micros.service;

import org.junit.Assert;
import org.junit.Test;

public class MessageRepositoryTest {

    @Test
    public void testGetDomain() {
        MessageRepository repository = new MessageRepository();
        MessageDomain domain = new MessageDomain();
        repository.addDomain("test", domain);
        Assert.assertNotNull(repository.getDomain("test"));
    }
}
