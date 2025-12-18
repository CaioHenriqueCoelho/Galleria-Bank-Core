package com.galleria.bank.repository.product;

import com.galleria.bank.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
