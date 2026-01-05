package com.gshop.orderservice.service.impl;

import com.gshop.orderservice.dto.OrderDto;
import com.gshop.orderservice.model.Order;
import com.gshop.orderservice.repository.OrderRepository;
import com.gshop.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(OrderDto.CreateOrderRequest request, String userId) {
        if (request.getOrderItems() == null || request.getOrderItems().isEmpty()) {
            throw new RuntimeException("No order items");
        }

        Order order = Order.builder()
                .user(userId)
                .orderItems(request.getOrderItems())
                .shippingAddress(request.getShippingAddress())
                .paymentMethod(request.getPaymentMethod())
                .taxPrice(request.getTaxPrice())
                .shippingPrice(request.getShippingPrice())
                .totalPrice(request.getTotalPrice())
                .isPaid(false)
                .isDelivered(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Order updateOrderToPaid(String id, OrderDto.PaymentResultRequest request) {
        Order order = getOrderById(id);

        order.setIsPaid(true);
        order.setPaidAt(LocalDateTime.now());
        order.setPaymentResult(new Order.PaymentResult(
                request.getId(),
                request.getStatus(),
                request.getUpdate_time(),
                request.getEmail_address()));

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderToDelivered(String id) {
        Order order = getOrderById(id);

        order.setIsDelivered(true);
        order.setDeliveredAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getMyOrders(String userId) {
        return orderRepository.findByUser(userId);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
