package com.example.cart.services;

import com.example.cart.dto.CartBasicInfo;
import com.example.cart.dto.CreateCart;
import com.example.cart.models.CartItem;
import com.example.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    private CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public String addToCart(CreateCart createCart, Long userId) {
        Optional<CartItem> optCartItem = cartRepository.findCartItemsByUserIdAndProductId(userId,createCart.getProductId());
        if(optCartItem.isPresent()){
            CartItem cartItem = optCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartRepository.save(cartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setProductId(createCart.getProductId());
            cartItem.setQuantity(1);
            cartItem.setUserId(userId);
            cartItem.setPrice(createCart.getPrice());
            cartRepository.save(cartItem);
        }
        return "Successfully added to the cart";
    }

    @Override
    public Page<CartItem> getCartItemsByUserId(int pageNum, int pageSize, String sortDir,  Long userId) {
        return cartRepository.findCartItemsByUserId(userId, PageRequest.of(pageNum,pageSize,sortDir.equalsIgnoreCase("asc")?
                Sort.by("createdAt").ascending() :
                Sort.by("createdAt").descending()));
    }

    @Override
    public CartBasicInfo getInfo(Long userId) {
        CartBasicInfo cartBasicInfo = new CartBasicInfo();
        List<CartItem> items= cartRepository.findCartItemsByUserId(userId);
        cartBasicInfo.setNumberOfItems(items.size());
//        cartBasicInfo.setTotalPrice);
        final double[] total = {0};
        items.forEach(item->{
            total[0] = total[0] + (item.getPrice()*item.getQuantity());
        });
        cartBasicInfo.setTotalPrice(total[0]);
        return cartBasicInfo;

    }

    @Override
    public String deleteCartItem(Long userId, Long id) {
        Optional<CartItem> optCartItem = cartRepository.findCartItemsByUserIdAndProductId(userId,id);
        if(optCartItem.isPresent()){
            CartItem cartItem = optCartItem.get();
            if(cartItem.getQuantity()>=2) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartRepository.save(cartItem);
            }
            else{
                cartRepository.delete(cartItem);
            }

            return "Successfully deleted from the cart";
        }
        return "Item Not Found";
    }


}
