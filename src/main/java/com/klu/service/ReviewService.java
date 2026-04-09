package com.klu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.klu.entity.Product;
import com.klu.entity.Review;
import com.klu.entity.User;
import com.klu.exception.ResourceNotFoundException;
import com.klu.repository.ProductRepository;
import com.klu.repository.ReviewRepository;
import com.klu.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Review addReview(Long customerId, Long productId, Review review) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        review.setCustomer(customer);
        review.setProduct(product);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}