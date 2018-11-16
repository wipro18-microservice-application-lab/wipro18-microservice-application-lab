package ch.hslu.wipro.micros.http;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 * This class represents a Grizzly http server instance.
 */
public class GrizzlyServerInstance {

    private ResourceConfig resourceConfig;
    private HttpServer server;

    /**
     * Creates a new GrizzlyServerInstance object. This method loads all webservice classes from the given package.
     * @param serviceResourcePackage package with webservice classes
     */
    public GrizzlyServerInstance(String serviceResourcePackage) {
        server = null;
        resourceConfig = new ResourceConfig()
                .register(MoxyJsonFeature.class)
                .register(CORSResponseFilter.class)
                .packages(serviceResourcePackage);
    }

    /**
     * Executes the http server in a new thread.
     */
    public void runServerNonBlocking() {
        // TODO Infos to configfile
        String uri = "http://0.0.0.0:8080/gateway";
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(uri), resourceConfig);
    }
}
