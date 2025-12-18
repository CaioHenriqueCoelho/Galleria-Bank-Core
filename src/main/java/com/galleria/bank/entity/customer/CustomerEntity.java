package com.galleria.bank.entity.customer;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
    name = "customers",
    uniqueConstraints = @UniqueConstraint(columnNames = "cpf")
)

public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column
    private String phone;

}
