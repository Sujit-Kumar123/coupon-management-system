package io.sujit.rest;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CouponRepository extends MongoRepository<Coupon, String> {

}
