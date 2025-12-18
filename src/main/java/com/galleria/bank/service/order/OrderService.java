package com.galleria.bank.service.order;

import com.galleria.bank.dto.order.OrderCreateDTO;
import com.galleria.bank.dto.order.OrderItemCreateDTO;
import com.galleria.bank.dto.order.OrderItemResponseDTO;
import com.galleria.bank.dto.order.OrderResponseDTO;
import com.galleria.bank.entity.customer.CustomerEntity;
import com.galleria.bank.entity.order.OrderEntity;
import com.galleria.bank.entity.order.OrderItemEntity;
import com.galleria.bank.entity.product.ProductEntity;
import com.galleria.bank.exception.ApiException;
import com.galleria.bank.repository.customer.CustomerRepository;
import com.galleria.bank.repository.order.OrderItemRepository;
import com.galleria.bank.repository.order.OrderRepository;
import com.galleria.bank.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponseDTO create(OrderCreateDTO dto) {

        CustomerEntity customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Customer not found")
                );

        OrderEntity order = new OrderEntity();
        order.setCustomer(customer);

        List<OrderItemEntity> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemCreateDTO itemDTO : dto.getItems()) {

            ProductEntity product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() ->
                            new ApiException("Produto com ID: " + itemDTO.getProductId() + "não encontrado", HttpStatus.NOT_FOUND)
                    );

            BigDecimal itemTotal =
                    product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));

            total = total.add(itemTotal);

            OrderItemEntity item = new OrderItemEntity();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setValue(product.getPrice());

            items.add(item);
        }

        order.setItems(items);
        order.setTotal(total);

        OrderEntity savedOrder = orderRepository.save(order);

        List<OrderItemResponseDTO> itemsResponse =
                savedOrder.getItems().stream()
                        .map(item -> new OrderItemResponseDTO(
                                item.getProduct().getId(),
                                item.getProduct().getDescription(),
                                item.getQuantity(),
                                item.getValue()
                        ))
                        .toList();

        return new OrderResponseDTO(
                savedOrder.getId(),
                customer.getName(),
                savedOrder.getTotal(),
                savedOrder.getCreatedAt(),
                itemsResponse
        );
    }


    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long id) {

        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException("Pedido não encontrado.", HttpStatus.NOT_FOUND)
                );

        List<OrderItemResponseDTO> items = order.getItems().stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getDescription(),
                        item.getQuantity(),
                        item.getValue()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getCustomer().getName(),
                order.getTotal(),
                order.getCreatedAt(),
                items
        );
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> findAll(Pageable pageable) {

        return orderRepository.findAll(pageable)
                .map(order -> {

                    List<OrderItemResponseDTO> items = order.getItems().stream()
                            .map(item -> new OrderItemResponseDTO(
                                    item.getProduct().getId(),
                                    item.getProduct().getDescription(),
                                    item.getQuantity(),
                                    item.getValue()
                            ))
                            .toList();

                    return new OrderResponseDTO(
                            order.getId(),
                            order.getCustomer().getName(),
                            order.getTotal(),
                            order.getCreatedAt(),
                            items
                    );
                });
    }

}

