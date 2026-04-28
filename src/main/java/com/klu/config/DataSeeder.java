package com.klu.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.klu.entity.Product;
import com.klu.entity.Role;
import com.klu.entity.User;
import com.klu.repository.ProductRepository;
import com.klu.repository.UserRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                // Ensure an artisan exists
                User artisan = userRepository.findByEmail("masterartisan@test.com").orElseGet(() -> {
                    User newUser = User.builder()
                            .name("Master Artisan")
                            .email("masterartisan@test.com")
                            .password("$2a$10$vI8aWNnFlbYapAH0Q/0fA.QO/5lK8XU.sY48j1tMh3P3Q3r0T.lG.") // encoded "password123"
                            .role(Role.ROLE_ARTISAN)
                            .build();
                    return userRepository.save(newUser);
                });

                Product p1 = Product.builder()
                        .name("Handwoven Naga Shawl")
                        .tribe("Naga")
                        .category("Clothing")
                        .stateName("Nagaland")
                        .imageUrl("https://images.unsplash.com/photo-1605022600390-071c6ef3518a?auto=format&fit=crop&w=500&q=80")
                        .price(2500.0)
                        .stock(15)
                        .customizable(false)
                        .description("Authentic handwoven tribal shawl with intricate geometric patterns.")
                        .artisan(artisan)
                        .build();

                Product p2 = Product.builder()
                        .name("Dhokra Brass Elephant")
                        .tribe("Gond")
                        .category("Home Decor")
                        .stateName("Chhattisgarh")
                        .imageUrl("https://images.unsplash.com/photo-1544439055-667746deaaec?auto=format&fit=crop&w=500&q=80")
                        .price(1800.0)
                        .stock(5)
                        .customizable(true)
                        .description("Traditional lost-wax casting brass artifact featuring ethnic motifs.")
                        .artisan(artisan)
                        .build();

                productRepository.save(p1);
                productRepository.save(p2);
                
                System.out.println("Dummy products seeded successfully!");
            }
        };
    }
}
