package com.tpspring.demo.controllers;

import com.tpspring.demo.domain.Cart;
import com.tpspring.demo.domain.CartItem;
import com.tpspring.demo.repositories.CartItemRepository;
import com.tpspring.demo.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @PostMapping(value = "/cart")
    public ResponseEntity<Cart> createNewCart()
    {
        Cart cart = cartRepository.save(new Cart());

        if (cart == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new cart");

        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
    }

    @GetMapping(value = "/cart/{id}")
    public Optional<Cart> getCart(@PathVariable Long id)
    {
        Optional<Cart> cart = cartRepository.findById(id);

        if (cart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");

        return cart;
    }

    @PostMapping(value = "/cart/{id}")
    @Transactional
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long id, @RequestBody CartItem cartItem)
    {
        System.out.println("ici");
        Cart cart = cartRepository.getOne(id);

        if (cart == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");


        cart.addProduct(cartItem);

        cartRepository.save(cart);

        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }
}
