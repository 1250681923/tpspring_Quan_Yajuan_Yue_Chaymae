package com.tpspring.demo.repositories;

import com.tpspring.demo.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<Cart, Long> {
}