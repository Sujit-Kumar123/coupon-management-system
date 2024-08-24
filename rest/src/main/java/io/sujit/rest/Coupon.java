package io.sujit.rest;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Coupon {
    @Id
    private String id;
    private String couponType;
    private List<Long> applicableProducts;
    private Double percentage;
    private Double flatAmount;
    private Double minCartValue;
    private List<Long> buyProducts;
    private List<Long> getProducts;
    private Integer limit;
    private Date validity;
}
