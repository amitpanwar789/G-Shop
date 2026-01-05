package com.gshop.orderservice.service;

import com.gshop.orderservice.dto.OrderDto;
import com.gshop.orderservice.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDto.CreateOrderRequest request, String userId);

    Order getOrderById(String id); // Security check inside or valid ownership

    Order updateOrderToPaid(String id, OrderDto.PaymentResultRequest request);

    Order updateOrderToDelivered(String id);

    List<Order> getMyOrders(String userId);

    List<Order> getOrders(); // Admin
}
