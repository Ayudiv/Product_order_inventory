package com.springbootbackendMicroservice.OrderService.Order.Service.controller;

import com.springbootbackendMicroservice.OrderService.Order.Service.dto.OrderRequest;
import com.springbootbackendMicroservice.OrderService.Order.Service.service.OrderService;
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
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "order placed successfully";
    }
}
