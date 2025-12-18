package com.galleria.bank.repository.order;

import com.galleria.bank.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByCustomerId(Long customerId);
}
