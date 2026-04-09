package com.klu.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.klu.entity.CartItem;
import com.klu.entity.OrderItem;
import com.klu.entity.Orders;
import com.klu.entity.User;
import com.klu.exception.BadRequestException;
import com.klu.exception.ResourceNotFoundException;
import com.klu.repository.CartItemRepository;
import com.klu.repository.OrderItemRepository;
import com.klu.repository.OrdersRepository;
import com.klu.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;

    public Orders placeOrder(Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        List<CartItem> cartItems = cartItemRepository.findByCustomerId(customerId);

        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        Orders order = Orders.builder()
                .customer(customer)
                .totalAmount(total)
                .status("PLACED")
                .orderDate(LocalDateTime.now())
                .build();

        Orders savedOrder = ordersRepository.save(order);

        for (CartItem item : cartItems) {
            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .price(item.getProduct().getPrice())
                    .customizationText(item.getCustomizationText())
                    .build();

            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteAll(cartItems);
        return savedOrder;
    }

    public List<Orders> getOrdersByCustomer(Long customerId) {
        return ordersRepository.findByCustomerId(customerId);
    }
}