package com.galleria.bank.repository.order;

import com.galleria.bank.entity.order.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    boolean existsByProductId(Long productId);
}

