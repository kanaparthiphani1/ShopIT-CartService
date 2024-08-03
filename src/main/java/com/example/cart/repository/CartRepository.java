package com.example.cart.repository;

import com.example.cart.models.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findCartItemsByUserIdAndProductId(Long userId, Long productId);
    Page<CartItem> findCartItemsByUserId(Long userId, PageRequest pageRequest);
    List<CartItem> findCartItemsByUserId(Long userId);
}
