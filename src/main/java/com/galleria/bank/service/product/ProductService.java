package com.galleria.bank.service.product;

import com.galleria.bank.dto.product.*;
import com.galleria.bank.entity.product.ProductEntity;
import com.galleria.bank.exception.ApiException;
import com.galleria.bank.repository.order.OrderItemRepository;
import com.galleria.bank.repository.product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final OrderItemRepository orderItemRepository;

    public ProductService(ProductRepository repository, OrderItemRepository orderItemRepository) {
        this.repository = repository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(p -> new ProductResponseDTO(
                        p.getId(),
                        p.getDescription(),
                        p.getValue()
                ));
    }

    @Transactional
    public ProductResponseDTO create(ProductCreateDTO dto) {

        ProductEntity produto = new ProductEntity();
        produto.setDescription(dto.getDescription());
        produto.setValue(dto.getValue());

        repository.save(produto);

        return new ProductResponseDTO(
                produto.getId(),
                produto.getDescription(),
                produto.getValue()
        );
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductUpdateDTO dto) {

        ProductEntity product = repository.findById(id)
                .orElseThrow(() -> new ApiException("Produto com ID: " + id + " não encontrado.", HttpStatus.NOT_FOUND));

        if (orderItemRepository.existsByProductId(id)) {
            throw new ApiException("Produto com ID: " + id + " possui pedidos e não pode ser atualizado.", HttpStatus.CONFLICT);
        }

        product.setDescription(dto.getDescription());
        product.setValue(dto.getValue());

        repository.save(product);

        return new ProductResponseDTO(
                product.getId(),
                product.getDescription(),
                product.getValue()
        );
    }


    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ApiException("Produto com ID: " + id + " não encontrado.", HttpStatus.NOT_FOUND);
        }

        if (orderItemRepository.existsByProductId(id)) {
            throw new ApiException("Produto com ID: " + id + " possui pedidos e não pode ser deletado.", HttpStatus.CONFLICT);
        }

        repository.deleteById(id);
    }
}
