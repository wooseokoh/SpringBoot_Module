package com.cloud.product.controller;

import com.cloud.api.dto.Product;
import com.cloud.product.domain.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class TestProductMapper {

    private ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    public void mapperTests(){

        Assertions.assertNotNull(mapper);

        Product dto = new Product(1, "name", "info", "serviceAddress");

        ProductEntity entity = mapper.dtoToEntity(dto);

        Assertions.assertEquals(dto.getProductId(),entity.getProductId());
        Assertions.assertEquals(dto.getProductName(),entity.getProductName());
        Assertions.assertEquals(dto.getProductInfo(),entity.getProductInfo());

        Product dto2 = mapper.entityToDto(entity);

        Assertions.assertEquals(entity.getProductId(),dto2.getProductId());
        Assertions.assertEquals(entity.getProductName(),dto2.getProductName());
        Assertions.assertEquals(entity.getProductInfo(),dto2.getProductInfo());
        Assertions.assertNull(dto2.getServiceAddress());
    }
}
