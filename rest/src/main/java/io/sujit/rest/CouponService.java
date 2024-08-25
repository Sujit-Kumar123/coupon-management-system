package io.sujit.rest;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CouponService {

    public Cart applyCoupon(Coupon coupon, Cart cart) {
        switch (coupon.getCouponType().toLowerCase()) {
            case "cart-wise":
                return applyCartWiseCoupon(coupon, cart);
            case "product-wise":
                return applyProductWiseCoupon(coupon, cart);
            case "bxgy":
                return applyBxGyCoupon(coupon, cart);
            default:
                throw new IllegalArgumentException("Invalid coupon type");
        }
    }

    private Cart applyCartWiseCoupon(Coupon coupon, Cart cart) {
        if (cart.getTotalAmount() > coupon.getMinCartValue()) {
            Double discount = cart.getTotalAmount() * (coupon.getPercentage() / 100);
            cart.setTotalAmount(cart.getTotalAmount() - discount);
        }
        return cart;
    }

    private Cart applyProductWiseCoupon(Coupon coupon, Cart cart) {
        List<CartItem> updatedItems = cart.getItems().stream()
                .map(item -> {
                    if (coupon.getApplicableProducts().contains(item.getProductId())) {
                        Double discount = item.getPrice() * (coupon.getPercentage() / 100);
                        item.setPrice(item.getPrice() - discount);
                    }
                    return item;
                })
                .collect(Collectors.toList());
        cart.setItems(updatedItems);
        updateCartTotal(cart);
        return cart;
    }

    private Cart applyBxGyCoupon(Coupon coupon, Cart cart) {
        long buyCount = cart.getItems().stream()
                .filter(item -> coupon.getBuyProducts().contains(item.getProductId()))
                .mapToInt(CartItem::getQuantity)
                .sum();

        long repetitionLimit = coupon.getLimit();
        long applicableTimes = buyCount / repetitionLimit;

        if (applicableTimes > 0) {
            AtomicLong getCount = new AtomicLong(applicableTimes);

            List<CartItem> updatedItems = cart.getItems().stream()
                    .map(item -> {
                        if (coupon.getGetProducts().contains(item.getProductId()) && getCount.get() > 0) {
                            item.setPrice(0.0);
                            getCount.decrementAndGet();
                        }
                        return item;
                    })
                    .collect(Collectors.toList());

            cart.setItems(updatedItems);
            updateCartTotal(cart);
        }

        return cart;
    }

    private void updateCartTotal(Cart cart) {
        Double total = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        cart.setTotalAmount(total);
    }
}
