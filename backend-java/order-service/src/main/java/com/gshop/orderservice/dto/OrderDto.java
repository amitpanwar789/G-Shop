package com.gshop.orderservice.dto;

import com.gshop.orderservice.model.OrderItem;
import com.gshop.orderservice.model.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateOrderRequest {
        private List<OrderItem> orderItems;
        private ShippingAddress shippingAddress;
        private String paymentMethod;
        private Double itemsPrice; // taxPrice, shippingPrice, totalPrice calculations should ideally be server
                                   // side for trust
        private Double taxPrice;
        private Double shippingPrice;
        private Double totalPrice;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentResultRequest {
        private String id;
        private String status;
        private String update_time;
        private String email_address;
    }
}
