package ch.hslu.wipro.micros.common.discovery;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DiscoverStrategyUrl implements DiscoverStrategy {
    private static final Logger logger = LogManager.getLogger(DiscoverStrategy.class);
    private final Gson gson = new Gson();

    @Override
    public ConnectionInfo get(String domain) {
        ConnectionInfo connectionInfo = new ConnectionInfo();

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpUriRequest getRequest = new HttpGet(DiscoveryConstants.ADDRESS + domain);
        getRequest.addHeader(HttpHeaders.ACCEPT, "application/json");

        try (CloseableHttpResponse httpResponse = httpClient.execute(getRequest)) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                String stringConnectionInfo = EntityUtils.toString(httpResponse.getEntity());
                JsonElement jsonRoot = new JsonParser().parse(stringConnectionInfo);

                connectionInfo = new Gson().fromJson(jsonRoot, ConnectionInfo.class);

                logger.info("received connection = {} with code = {}",
                        stringConnectionInfo, statusCode);
            }

            if (statusCode == 404) {
                logger.info("no connection info found for domain = {}",
                        domain);
            }

        } catch (IOException e) {
            logger.error("There was a problem communicating with the discovery service");
        }

        return connectionInfo;
    }

    @Override
    public void put(String domain, ConnectionInfo connectionInfo) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut putRequest = new HttpPut(DiscoveryConstants.ADDRESS + domain);

        try {
            putRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            putRequest.setEntity(new StringEntity(gson.toJson(connectionInfo)));

            CloseableHttpResponse httpResponse = httpClient.execute(putRequest);

            String content = EntityUtils.toString(httpResponse.getEntity());
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            logger.info("registered as domain = {}", content);
            logger.info("reply code from discovery service = {}", statusCode);

        } catch (IOException e) {
            logger.error("There was a problem registering domain");
        }
    }
}