package com.gshop.productservice.controller;

import com.gshop.productservice.dto.ProductDto;
import com.gshop.productservice.model.Product;
import com.gshop.productservice.service.ProductService;
import com.gshop.productservice.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(productService.getProducts(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body(java.util.Collections.singletonMap("message", "Product removed"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId == null) {
            // Fallback if attribute not set (should be set in filter)
            // Try to parse header again or assume context has it?
            // Since filter sets Authentication, we can't easily get extra claims from
            // Principal string.
            // We can parse header again.
            String headerAuth = request.getHeader("Authorization");
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                String token = headerAuth.substring(7);
                userId = (String) jwtUtils.extractAllClaims(token).get("id");
            }
        }
        return ResponseEntity.status(201).body(productService.createProduct(userId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable String id,
            @RequestBody ProductDto.CreateProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<?> createReview(@PathVariable String id, @RequestBody ProductDto.ReviewRequest request,
            HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").substring(7);
        Claims claims = jwtUtils.extractAllClaims(token);
        String userId = (String) claims.get("id");
        String userName = (String) claims.get("name");

        productService.createReview(id, request, userId, userName);
        return ResponseEntity.status(201).body(java.util.Collections.singletonMap("message", "Review added"));
    }

    @GetMapping("/top")
    public ResponseEntity<List<Product>> getTopProducts() {
        return ResponseEntity.ok(productService.getTopProducts());
    }
}
