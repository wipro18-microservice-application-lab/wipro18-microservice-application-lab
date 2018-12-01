package ch.hslu.wipro.micros.service.repository;

import ch.hslu.wipro.micros.model.order.OrderDto;
import ch.hslu.wipro.micros.model.order.OrderDtoBuilder;
import ch.hslu.wipro.micros.model.order.OrderUpdateDto;
import ch.hslu.wipro.micros.model.order.OrderUpdateResultDto;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderRepositoryServiceTest {
    private OrderRepositoryService orderRepositoryService;
    private static int amountOfOrderDto = 0;
    private long orderDtoId;
    private OrderDto orderDto;

    @Before
    public void setUp() throws Exception {
        Map<Long, Integer> amountToArticle = new HashMap<>();
        amountToArticle.put(1L, 5);
        amountToArticle.put(2L, 4);
        amountToArticle.put(3L, 1);

        orderDto = new OrderDtoBuilder()
                .atStatus("Open")
                .atCustomer(42L)
                .atTotalPrice(BigDecimal.valueOf(120L))
                .mapAmountToArticle(amountToArticle)
                .build();

        orderRepositoryService = new OrderRepositoryService();
        orderDtoId = orderRepositoryService.setOrder(orderDto);
        amountOfOrderDto++;
    }

    @Test
    public void getOrder() {
        OrderDto retrievedOrderDto = orderRepositoryService.getOrder(orderDtoId);
        orderDto.setOrderId(orderDtoId);

        assertEquals(orderDto, retrievedOrderDto);
    }

    @Test
    public void setOrder() {
        OrderDto newOrderDto = new OrderDtoBuilder().build();
        long newOrderDtoId = orderRepositoryService.setOrder(newOrderDto);
        newOrderDto.setOrderId(newOrderDtoId);

        OrderDto retrievedOrderDto = orderRepositoryService.getOrder(newOrderDtoId);
        assertEquals(newOrderDto, retrievedOrderDto);
    }

    @Test
    public void getAllOrders() {
        List<OrderDto> orderDtos = orderRepositoryService.getAllOrders();
        assertEquals(amountOfOrderDto, orderDtos.size());
    }

    @Test
    public void getAllOrdersByCustomerId() {
        List<OrderDto> orderDtos = orderRepositoryService.getAllOrdersByCustomerId(42L);
        assertEquals(amountOfOrderDto, orderDtos.size());
    }

    @Test
    public void updateStatus() {
        final String NEW_UPDATE = "New Update";

        OrderUpdateDto orderUpdateDto = new OrderUpdateDto();
        orderUpdateDto.setStatus(NEW_UPDATE);
        orderUpdateDto.setOrderId(0);

        OrderUpdateResultDto orderUpdateResultDto = orderRepositoryService.updateStatus(orderUpdateDto);
        assertEquals("successful", orderUpdateResultDto.getResult());

        OrderDto retrievedOrderDto = orderRepositoryService.getOrder(orderUpdateDto.getOrderId());
        assertEquals(NEW_UPDATE, retrievedOrderDto.getStatus());
    }
}