package ch.hslu.wipro.micros.common.discovery;

import org.apache.logging.log4j.core.util.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

public class DiscoverStrategyUrl implements DiscoverStrategy {

    @Override
    public String retrieve(String domain) {
        StringWriter writer = new StringWriter();

        try {
            URL url = new URL(DiscoveryConstants.ADDRESS + domain);
            InputStreamReader reader = new InputStreamReader(url.openStream());

            IOUtils.copy(reader, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }
}
