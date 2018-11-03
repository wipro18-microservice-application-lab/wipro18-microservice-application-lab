package ch.hslu.wipro.micros.common.dto;

import java.util.List;

public class OrderDto {
    private List<Integer> articles;
    private int customer;
    private double price;
    private OrderState state;

    public OrderDto(List<Integer> articles, int customer, double price, OrderState state) {
        this.articles = articles;
        this.customer = customer;
        this.price = price;
        this.state = state;
    }

    public List<Integer> getArticles() {
        return articles;
    }

    public void setArticles(List<Integer> articles) {
        this.articles = articles;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }
}