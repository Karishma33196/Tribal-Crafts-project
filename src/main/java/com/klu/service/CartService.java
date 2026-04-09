package com.klu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.klu.entity.CartItem;
import com.klu.entity.Product;
import com.klu.entity.User;
import com.klu.exception.ResourceNotFoundException;
import com.klu.repository.CartItemRepository;
import com.klu.repository.ProductRepository;
import com.klu.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartItem addToCart(Long customerId, Long productId, Integer quantity, String customizationText) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        CartItem item = new CartItem();
        item.setCustomer(customer);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setCustomizationText(customizationText);

        return cartItemRepository.save(item);
    }

    public List<CartItem> getCartByCustomer(Long customerId) {
        return cartItemRepository.findByCustomerId(customerId);
    }

    public void removeCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new ResourceNotFoundException("Cart item not found with id: " + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
    }
}