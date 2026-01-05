package com.gshop.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String user; // Admin User ID who created it

    private String name;
    private String image;
    private String brand;
    private String category;
    private String description;

    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    private Double rating = 0.0;

    @Builder.Default
    private Integer numReviews = 0;

    @Builder.Default
    private Double price = 0.0;

    @Builder.Default
    private Integer countInStock = 0;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
