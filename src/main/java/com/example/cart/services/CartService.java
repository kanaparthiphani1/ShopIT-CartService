package com.example.cart.services;

import com.example.cart.dto.CartBasicInfo;
import com.example.cart.dto.CreateCart;
import com.example.cart.models.CartItem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {
    public String addToCart(CreateCart createCart, Long userId);
    public Page<CartItem> getCartItemsByUserId(int pageNum, int pageSize, String sortDir, Long userId);
    public CartBasicInfo getInfo(Long userId);
    public String deleteCartItem(Long userId, Long id);
}
