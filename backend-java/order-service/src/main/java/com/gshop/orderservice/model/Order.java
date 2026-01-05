package com.gshop.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String user; // User ID

    private List<OrderItem> orderItems;
    private ShippingAddress shippingAddress;
    private String paymentMethod;

    private PaymentResult paymentResult;

    private Double taxPrice;
    private Double shippingPrice;
    private Double totalPrice;

    @Builder.Default
    private Boolean isPaid = false;
    private LocalDateTime paidAt;

    @Builder.Default
    private Boolean isDelivered = false;
    private LocalDateTime deliveredAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentResult {
        private String id;
        private String status;
        private String update_time;
        private String email_address;
    }
}
