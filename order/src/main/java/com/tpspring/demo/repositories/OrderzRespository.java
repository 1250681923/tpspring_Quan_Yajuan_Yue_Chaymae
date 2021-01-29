package com.tpspring.demo.repositories;

import com.tpspring.demo.domain.Orderz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderzRespository extends JpaRepository<Orderz, Long> {
}
