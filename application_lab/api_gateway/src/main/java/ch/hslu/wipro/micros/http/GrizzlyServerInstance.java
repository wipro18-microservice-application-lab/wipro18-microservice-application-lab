package ch.hslu.wipro.micros.http;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class GrizzlyServerInstance {

    private ResourceConfig resourceConfig;
    private HttpServer server;

    public GrizzlyServerInstance(String serviceResourcePackage) {
        server = null;
        resourceConfig = new ResourceConfig()
                .register(MoxyJsonFeature.class)
                .packages(serviceResourcePackage);
    }

    public void runServerNonBlocking() {
        // TODO Infos to configfile
        String uri = "http://0.0.0.0:8080/gateway";
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(uri), resourceConfig);
    }
}
