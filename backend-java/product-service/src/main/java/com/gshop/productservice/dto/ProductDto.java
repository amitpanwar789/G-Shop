package com.gshop.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public class ProductDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductResponse {
        private String _id;
        private String user;
        private String name;
        private String image;
        private String brand;
        private String category;
        private String description;
        private Double rating;
        private Integer numReviews;
        private Double price;
        private Integer countInStock;
        private List<ReviewDto> reviews;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateProductRequest {
        private String name;
        private String price;
        private String image;
        private String brand;
        private String category;
        private String countInStock;
        private String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewDto {
        private String _id; // Usually review ID if needed
        private String name;
        private Double rating;
        private String comment;
        private String user;
        private String createdAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewRequest {
        private Double rating;
        private String comment;
    }
}
