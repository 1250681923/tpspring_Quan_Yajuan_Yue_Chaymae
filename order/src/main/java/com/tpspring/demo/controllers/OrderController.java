package com.tpspring.demo.controllers;

import com.tpspring.demo.domain.OrderItem;
import com.tpspring.demo.domain.Orderz;
import com.tpspring.demo.repositories.OrderItemRepository;
import com.tpspring.demo.repositories.OrderzRespository;
import org.hibernate.criterion.Order;
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
    OrderzRespository orderzRespository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PostMapping(value = "/order")
    public ResponseEntity<Orderz> createNewOrder()
    {
        Orderz order = orderzRespository.save(new Orderz());

        if (order == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new order");

        return new ResponseEntity<Orderz>(order, HttpStatus.CREATED);
    }


    @GetMapping(value = "/order/{orderId}")
    public Optional<Orderz> getOrder(@PathVariable Long orderId)
    {
        Optional<Orderz> orderz = orderzRespository.findById(orderId);

        if (orderz == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");

        return orderz;
    }



    @GetMapping(value = "/order")
    public List<Orderz> getAllOrder()
    {
        List<Orderz> orderList = orderzRespository.findAll();

        return orderList;
    }

    @PostMapping(value = "/order/{cartId}/{motant}/{orderId}")
    @Transactional
    public ResponseEntity<OrderItem> addProductToOrder( @RequestBody OrderItem orderItem,@PathVariable Long cartId,@PathVariable Long orderId, @PathVariable double motant)
    {

        Orderz orderz = orderzRespository.getOne(orderId);

        if (orderz == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");

        orderItemRepository.save(orderItem);
        orderz.addProducts(orderItem);

        orderz.setMontant(motant);
        orderzRespository.save(orderz);

        return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
    }
}

