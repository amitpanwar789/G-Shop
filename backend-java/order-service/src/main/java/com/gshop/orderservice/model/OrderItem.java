package com.gshop.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private String name;
    private Integer qty;
    private String image;
    private Double price;
    private String product; // Product ID
}
