package com.gshop.productservice.service.impl;

import com.gshop.productservice.dto.ProductDto;
import com.gshop.productservice.model.Product;
import com.gshop.productservice.model.Review;
import com.gshop.productservice.repository.ProductRepository;
import com.gshop.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts(String keyword) {
        // Simple search implementation
        // For advanced search with keyword (regex), we'd need custom query or
        // ExampleMatcher.
        // Node implementation: regex search on name.
        // For MV, I will just return all.
        // TODO: Implement regex search
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void deleteProduct(String id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    // Admin creates an empty sample product
    @Override
    public Product createProduct(String userId) {
        Product product = Product.builder()
                .user(userId)
                .name("Sample name")
                .price(0.0)
                .image("/images/sample.jpg")
                .brand("Sample brand")
                .category("Sample category")
                .countInStock(0)
                .numReviews(0)
                .description("Sample description")
                .rating(0.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String id, ProductDto.CreateProductRequest request) {
        Product product = getProductById(id);

        product.setName(request.getName());
        product.setPrice(Double.valueOf(request.getPrice()));
        product.setDescription(request.getDescription());
        product.setImage(request.getImage());
        product.setBrand(request.getBrand());
        product.setCategory(request.getCategory());
        product.setCountInStock(Integer.valueOf(request.getCountInStock()));
        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    @Override
    public void createReview(String id, ProductDto.ReviewRequest request, String userId, String userName) {
        Product product = getProductById(id);

        boolean alreadyReviewed = product.getReviews().stream()
                .anyMatch(r -> r.getUser().equals(userId));

        if (alreadyReviewed) {
            throw new RuntimeException("Product already reviewed"); // status 400
        }

        Review review = Review.builder()
                .name(userName)
                .rating(request.getRating())
                .comment(request.getComment())
                .user(userId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        product.getReviews().add(review);
        product.setNumReviews(product.getReviews().size());

        double ratingSum = product.getReviews().stream()
                .mapToDouble(Review::getRating)
                .sum();
        product.setRating(ratingSum / product.getReviews().size());

        productRepository.save(product);
    }

    @Override
    public List<Product> getTopProducts() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "rating")).stream()
                .limit(3)
                .collect(Collectors.toList());
    }
}
