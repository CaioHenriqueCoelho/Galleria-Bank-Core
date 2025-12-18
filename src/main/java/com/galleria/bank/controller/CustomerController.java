package com.galleria.bank.controller;

import com.galleria.bank.dto.customer.*;
import com.galleria.bank.service.customer.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponseDTO>> getAll(
            @PageableDefault(
                    size = 10,
                    sort = "name",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        Page<CustomerResponseDTO> customers = service.findAll(pageable);
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(
            @RequestBody @Valid CustomerCreateDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid CustomerUpdateDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

