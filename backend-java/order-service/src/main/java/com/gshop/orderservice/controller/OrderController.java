package com.gshop.orderservice.controller;

import com.gshop.orderservice.dto.OrderDto;
import com.gshop.orderservice.model.Order;
import com.gshop.orderservice.service.OrderService;
import com.gshop.orderservice.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtils jwtUtils;

    private String getUserId(HttpServletRequest request) {
        // Fallback or prefer attribute set by filter
        String userId = (String) request.getAttribute("userId");
        if (userId == null) {
            String token = request.getHeader("Authorization").substring(7);
            userId = (String) jwtUtils.extractAllClaims(token).get("id");
        }
        return userId;
    }

    @PostMapping
    public ResponseEntity<Order> addOrderItems(HttpServletRequest request,
            @RequestBody OrderDto.CreateOrderRequest orderRequest) {
        String userId = getUserId(request);
        return ResponseEntity.status(201).body(orderService.createOrder(orderRequest, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id, HttpServletRequest request) {
        Order order = orderService.getOrderById(id);
        String userId = getUserId(request);

        // Security check: Only Admin or Owner can view
        // IsAdmin check needed or rely on PreAuthorize?
        // Node implementation checked if user.isAdmin OR
        // order.user._id.equals(req.user._id)
        String token = request.getHeader("Authorization").substring(7);
        Boolean isAdmin = (Boolean) jwtUtils.extractAllClaims(token).get("isAdmin");

        if (Boolean.TRUE.equals(isAdmin) || order.getUser().equals(userId)) {
            return ResponseEntity.ok(order);
        } else {
            throw new RuntimeException("Not authorized");
        }
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<Order> updateOrderToPaid(@PathVariable String id,
            @RequestBody OrderDto.PaymentResultRequest request) {
        return ResponseEntity.ok(orderService.updateOrderToPaid(id, request));
    }

    @PutMapping("/{id}/deliver")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrderToDelivered(@PathVariable String id) {
        return ResponseEntity.ok(orderService.updateOrderToDelivered(id));
    }

    @GetMapping("/myorders")
    public ResponseEntity<List<Order>> getMyOrders(HttpServletRequest request) {
        String userId = getUserId(request);
        return ResponseEntity.ok(orderService.getMyOrders(userId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }
}
