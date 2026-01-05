package com.gshop.productservice.service;

import com.gshop.productservice.dto.ProductDto;
import com.gshop.productservice.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(String keyword);

    Product getProductById(String id);

    void deleteProduct(String id);

    Product createProduct(String userId);

    Product updateProduct(String id, ProductDto.CreateProductRequest request);

    void createReview(String id, ProductDto.ReviewRequest request, String userId, String userName);

    List<Product> getTopProducts();
}
