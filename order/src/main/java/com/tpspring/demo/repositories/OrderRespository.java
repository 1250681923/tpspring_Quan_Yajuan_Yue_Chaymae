package com.tpspring.demo.repositories;

import com.tpspring.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRespository extends JpaRepository<Order, Long> {
}
