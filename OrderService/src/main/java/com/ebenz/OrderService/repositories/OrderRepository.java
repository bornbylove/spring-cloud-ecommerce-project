package com.ebenz.OrderService.repositories;

import com.ebenz.OrderService.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
