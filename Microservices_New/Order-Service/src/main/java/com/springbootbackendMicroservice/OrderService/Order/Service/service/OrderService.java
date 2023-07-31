package com.springbootbackendMicroservice.OrderService.Order.Service.service;


import com.springbootbackendMicroservice.OrderService.Order.Service.dto.InventoryResponse;
import com.springbootbackendMicroservice.OrderService.Order.Service.dto.OrderLineItemsDto;
import com.springbootbackendMicroservice.OrderService.Order.Service.dto.OrderRequest;
import com.springbootbackendMicroservice.OrderService.Order.Service.model.Order;
import com.springbootbackendMicroservice.OrderService.Order.Service.model.OrderLineItems;
import com.springbootbackendMicroservice.OrderService.Order.Service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest)  {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto).toList();

        order.setOrderLineItemsList(orderLineItems);
        List<String> skucodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkucode).toList();
        //call inventory service place order if product is in stock
        InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get().uri("http://Inventory-Service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skucode",skucodes).build())
                .retrieve().bodyToMono(InventoryResponse[].class)
                .block();
        Boolean allProductInStock=Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);

        if (allProductInStock) {
            orderRepository.save(order);
        }else{
            try {
                throw new Exception("Product is not in stock , Please try again later");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkucode(orderLineItemsDto.getSkucode());
        return orderLineItems;
    }
}
