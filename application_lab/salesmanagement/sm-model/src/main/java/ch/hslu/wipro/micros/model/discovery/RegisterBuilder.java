package ch.hslu.wipro.micros.model.discovery;

import java.util.List;

public class RegisterBuilder {
    private String domain;
    private String exchange;
    private List<String> topics;

    public RegisterBuilder forDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public RegisterBuilder withExchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    public RegisterBuilder withTopics(List<String> topics) {
        this.topics = topics;
        return this;
    }

    public RegisterDto build() {
        RegisterDto registerInformation = new RegisterDto();
        registerInformation.setDomain(domain);
        registerInformation.setExchange(exchange);
        registerInformation.setTopics(topics);

        return registerInformation;
    }
}
