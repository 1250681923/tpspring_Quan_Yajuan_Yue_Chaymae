package com.tpspring.demo.controllers;

import com.tpspring.demo.domain.Order;
import com.tpspring.demo.domain.OrderItem;
import com.tpspring.demo.repositories.OrderItemRepository;
import com.tpspring.demo.repositories.OrderRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    @Autowired
    OrderRespository orderRespository;

    @Autowired
    OrderItemRepository orderItemRepository;

//    @PostMapping(value = "/cart")
//    public ResponseEntity<Order> createNewOrder()
//    {
//        Cart cart = cartRepository.save(new Cart());
//
//        if (cart == null)
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new cart");
//
//        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
//    }

    @GetMapping(value = "/order")
    public List<Order> getAllOrder()
    {
        List<Order> orderList = orderRespository.findAll();

        return orderList;
    }

    @PostMapping(value = "/order/{cartId}/{montant}/{a}")
    @Transactional
    public ResponseEntity<OrderItem> addProductToOrder(@PathVariable Long cartId, @RequestBody OrderItem orderItem,@PathVariable int a, @PathVariable double montant)
    {
        if (a == 1){
            Order order = orderRespository.save(new Order());
        }

        Order order = orderRespository.getOne(cartId);

        if (order == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");

        order.addProducts(orderItem);

        order.setMontant(montant);
        orderRespository.save(order);

        return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
    }
}

