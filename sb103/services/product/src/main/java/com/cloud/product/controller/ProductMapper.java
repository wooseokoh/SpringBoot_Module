package com.cloud.product.controller;

import com.cloud.api.dto.Product;
import com.cloud.product.domain.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({})
    Product entityToDto(ProductEntity entityt);

    @Mappings({
            @Mapping(target ="id", ignore = true),
            @Mapping(target ="version", ignore = true)
    })
    ProductEntity dtoToEntity(Product api);
}

