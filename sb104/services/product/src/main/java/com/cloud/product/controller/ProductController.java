package com.cloud.product.controller;

import com.cloud.api.controller.ProductControllerInterface;
import com.cloud.api.dto.Product;
import com.cloud.api.exception.InvalidInputException;
import com.cloud.api.exception.NotFoundException;
import com.cloud.product.domain.ProductEntity;
import com.cloud.product.domain.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class ProductController implements ProductControllerInterface {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public Product createProduct(Product body) {
        try {
            ProductEntity entity = mapper.dtoToEntity(body);
            ProductEntity newEntity = repository.save(entity);
            log.debug("#createProduct=========================");
            log.debug(newEntity.toString());
            log.debug("#======================================");
            return mapper.entityToDto(newEntity);
        }catch (DuplicateKeyException dke){
         throw new InvalidInputException("Duplicate Product Id: " + body.getProductId());
        }
    }

    @Override
    public Product getProduct(int productId) {
        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        ProductEntity entity = repository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException("No productId: " + productId));
        Product product = mapper.entityToDto(entity);
        log.debug("getProduct: {}", product.getProductId());
        return product;
    }

    @Override
    public void deleteProduct(int productId) {
        log.debug("deleteProduct: {}", productId);
        repository.findByProductId(productId).ifPresent(e -> repository.delete(e));
    }
}
