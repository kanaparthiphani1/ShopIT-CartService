package com.example.cart.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends BaseModel{

    private Long userId;
    private Long productId;
    private int quantity;
    private double price;

}
