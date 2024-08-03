package com.example.cart.controllers;

import com.example.cart.dto.CartBasicInfo;
import com.example.cart.dto.CreateCart;
import com.example.cart.models.CartItem;
import com.example.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping()
    public ResponseEntity<String> createCart(@RequestBody CreateCart createCart, @AuthenticationPrincipal Jwt jwt){

        Long userId = (Long) jwt.getClaims().get("userId");

        return ResponseEntity.ok(this.cartService.addToCart(createCart,userId));

    }

    @GetMapping()
    public Page<CartItem> getCart(@RequestParam("pageNumber") int pageNumber,
                                  @RequestParam("pageSize") int pageSize,
                                  @RequestParam("sortDir") String sortDir,
                                  @AuthenticationPrincipal Jwt jwt){

        Long userId = (Long) jwt.getClaims().get("userId");

        return cartService.getCartItemsByUserId(pageNumber,pageSize,sortDir,userId);

    }

    @GetMapping("/info")
    public ResponseEntity<CartBasicInfo> getCartInfo(@AuthenticationPrincipal Jwt jwt){
        Long userId = (Long) jwt.getClaims().get("userId");

        return ResponseEntity.ok(cartService.getInfo(userId));
    }


    @DeleteMapping("")
    public ResponseEntity<String> deleteProduct(@RequestParam("prodId") Long id, @AuthenticationPrincipal Jwt jwt){
        Long userId = (Long) jwt.getClaims().get("userId");
        return ResponseEntity.ok(cartService.deleteCartItem(userId,id));
    }

}
