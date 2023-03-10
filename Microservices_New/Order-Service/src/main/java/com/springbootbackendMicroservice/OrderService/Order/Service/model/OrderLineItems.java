package com.springbootbackendMicroservice.OrderService.Order.Service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.security.DenyAll;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="t_order_line_items")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skucode;
    private BigDecimal price;
    private Integer quantity;
}
