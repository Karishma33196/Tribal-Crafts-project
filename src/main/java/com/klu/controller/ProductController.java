package com.klu.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.klu.entity.Product;
import com.klu.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Product>> searchProducts(@PathVariable String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @GetMapping("/artisan/{artisanId}")
    public ResponseEntity<List<Product>> getProductsByArtisan(@PathVariable Long artisanId) {
        return ResponseEntity.ok(productService.getProductsByArtisan(artisanId));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ARTISAN','ROLE_ADMIN')")
    @PostMapping("/artisan/{artisanId}")
    public ResponseEntity<Product> addProduct(@RequestBody Product product, @PathVariable Long artisanId) {
        return ResponseEntity.ok(productService.addProduct(product, artisanId));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ARTISAN','ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ARTISAN','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}