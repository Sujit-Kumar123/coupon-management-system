package io.sujit.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;
    private CouponService couponService;

    @GetMapping("/")
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @GetMapping("/{id}")
    public Coupon getCoupon(@PathVariable String id) {
        return couponRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Coupon createCoupon(@RequestBody Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @PutMapping("/{id}")
    public Coupon updateCoupon(@PathVariable String id, @RequestBody Coupon coupon) {
        Coupon existingCoupon = couponRepository.findById(id).orElse(null);
        if (existingCoupon != null) {
            existingCoupon.setCouponType(coupon.getCouponType());
            existingCoupon.setApplicableProducts(coupon.getApplicableProducts());
            existingCoupon.setPercentage(coupon.getPercentage());
            existingCoupon.setFlatAmount(coupon.getFlatAmount());
            existingCoupon.setMinCartValue(coupon.getMinCartValue());
            existingCoupon.setBuyProducts(coupon.getBuyProducts());
            existingCoupon.setGetProducts(coupon.getGetProducts());
            existingCoupon.setLimit(coupon.getLimit());
            existingCoupon.setValidity(coupon.getValidity());
            return couponRepository.save(existingCoupon);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteCoupon(@PathVariable String id) {
        couponRepository.deleteById(id);
        return id;
    }
    @PostMapping("/apply-coupon/{id}")
    public Cart applyCoupon(@PathVariable String id, @RequestBody Cart cart) {
        Coupon coupon = couponRepository.findById(id).orElse(null);
        if (coupon != null) {
            return couponService.applyCoupon(coupon, cart);
        } else {
            throw new RuntimeException("Coupon not found");
        }
    }
}
