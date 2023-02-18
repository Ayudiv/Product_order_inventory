package com.springbootbackendMicroservice.OrderService.Order.Service.repository;

import com.springbootbackendMicroservice.OrderService.Order.Service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
