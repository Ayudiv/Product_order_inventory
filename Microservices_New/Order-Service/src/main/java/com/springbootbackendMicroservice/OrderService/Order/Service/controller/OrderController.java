package com.springbootbackendMicroservice.OrderService.Order.Service.controller;

import com.springbootbackendMicroservice.OrderService.Order.Service.dto.OrderRequest;
import com.springbootbackendMicroservice.OrderService.Order.Service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="inventory",fallbackMethod = "fallbackmethod")
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "order placed successfully";
    }

    public String fallbackmethod(OrderRequest orderRequest,RuntimeException runtimeException){
        return "Oops ! Something went wrong , Please order after sometime";
    }
}
