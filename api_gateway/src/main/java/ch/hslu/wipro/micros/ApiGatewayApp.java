package ch.hslu.wipro.micros;

import ch.hslu.wipro.micros.http.GrizzlyServerInstance;

public class ApiGatewayApp {

    public static void main(String[] args) {
        GrizzlyServerInstance grizzlyServer = new GrizzlyServerInstance("ch.hslu.wipro.micros.service");
        grizzlyServer.runServerNonBlocking();
    }
}
