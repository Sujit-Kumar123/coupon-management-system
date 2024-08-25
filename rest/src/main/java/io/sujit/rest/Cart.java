package io.sujit.rest;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    private String id;
    private Long user;
    private List<CartItem> items;
    private Double totalAmount;
    private Long coupon;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
class CartItem {
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
