package com.galleria.bank.service.customer;

import com.galleria.bank.dto.customer.CustomerCreateDTO;
import com.galleria.bank.dto.customer.CustomerResponseDTO;
import com.galleria.bank.dto.customer.CustomerUpdateDTO;
import com.galleria.bank.entity.customer.CustomerEntity;
import com.galleria.bank.exception.ApiException;

import com.galleria.bank.repository.customer.CustomerRepository;
import com.galleria.bank.repository.order.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository repository, OrderRepository orderRepository) {
        this.repository = repository;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(c -> new CustomerResponseDTO(
                        c.getId(),
                        c.getName(),
                        c.getCpf(),
                        c.getPhone()
                ));
    }

    @Transactional
    public CustomerResponseDTO create(CustomerCreateDTO dto) {

        String cpf = dto.getCpf().replaceAll("\\D", "");

        if (repository.existsByCpf(cpf)) {
            throw new ApiException("CPF Já registrado!", HttpStatus.CONFLICT);
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setName(dto.getName());
        customer.setCpf(cpf);
        customer.setPhone(dto.getPhone());

        repository.save(customer);

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getPhone()
        );
    }


    @Transactional
    public CustomerResponseDTO update(Long id, CustomerUpdateDTO dto) {

        CustomerEntity customer = repository.findById(id)
                .orElseThrow(() -> new ApiException("Cliente não encontrado!", HttpStatus.NOT_FOUND));

        String newCpf = dto.getCpf().replaceAll("\\D", "");
        String oldCpf = customer.getCpf().replaceAll("\\D", "");

        if (!oldCpf.equals(newCpf) && repository.existsByCpf(newCpf)) {
            throw new ApiException("CPF Já registrado!", HttpStatus.CONFLICT);
        }

        customer.setName(dto.getName());
        customer.setCpf(dto.getCpf().replaceAll("\\D", ""));
        customer.setPhone(dto.getPhone());

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getPhone()
        );
    }

    @Transactional
    public void deleteById(Long id) {

        if (!repository.existsById(id)) {
            throw new ApiException("Cliente não encontrado!", HttpStatus.NOT_FOUND);
        }

        if (orderRepository.existsByCustomerId(id)) {
            throw new ApiException("Cliente com id: " + id + " possui pedidos e não pode ser deletado.", HttpStatus.CONFLICT);
        }

        repository.deleteById(id);
    }
}

