package ch.hslu.wipro.micros.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link ch.hslu.wipro.micros.util.ConfigManager}
 */
public class ConfigManagerTest {

    @Test
    public void testDefaultConfig() {
        ConfigManager configManager = new ConfigManager();
        Assert.assertEquals("http://0.0.0.0:8080/gateway", configManager.getGatewayUriFromProps());
    }
}
