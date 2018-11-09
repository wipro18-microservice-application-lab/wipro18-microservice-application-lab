package ch.hslu.wipro.micros.model.discovery;

import java.util.List;

public class RegisterDto {
    private String domain;
    private String exchange;
    private List<String> topics;

    void setDomain(String domain) {
        this.domain = domain;
    }

    void setExchange(String exchange) {
        this.exchange = exchange;
    }

    void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getDomain() {
        return domain;
    }

    public String getExchange() {
        return exchange;
    }

    public List<String> getTopics() {
        return topics;
    }
}
