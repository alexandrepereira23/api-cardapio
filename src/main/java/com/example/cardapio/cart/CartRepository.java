package com.example.cardapio.cart;

import com.example.cardapio.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    void deleteByUser(User user);
}
