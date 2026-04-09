package com.klu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.klu.entity.Product;
import com.klu.entity.User;
import com.klu.exception.ResourceNotFoundException;
import com.klu.repository.ProductRepository;
import com.klu.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Product> getProductsByArtisan(Long artisanId) {
        return productRepository.findByArtisanId(artisanId);
    }

    public Product addProduct(Product product, Long artisanId) {
        User artisan = userRepository.findById(artisanId)
                .orElseThrow(() -> new ResourceNotFoundException("Artisan not found with id: " + artisanId));

        product.setArtisan(artisan);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updated) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setName(updated.getName());
        product.setTribe(updated.getTribe());
        product.setCategory(updated.getCategory());
        product.setStateName(updated.getStateName());
        product.setImageUrl(updated.getImageUrl());
        product.setPrice(updated.getPrice());
        product.setStock(updated.getStock());
        product.setCustomizable(updated.getCustomizable());
        product.setDescription(updated.getDescription());
        product.setMaterials(updated.getMaterials());
        product.setCareInstructions(updated.getCareInstructions());

        return productRepository.save(product);
    }

    public String deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
        return "Product deleted successfully";
    }
}