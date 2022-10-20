package com.cloud.reactor2.repository;

import com.cloud.reactor2.domain.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CartReactiveRepository extends ReactiveCrudRepository<Cart, String> {
}
