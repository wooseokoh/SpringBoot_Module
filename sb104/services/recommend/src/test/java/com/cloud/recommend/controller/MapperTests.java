package com.cloud.recommend.controller;

import com.cloud.api.dto.Product;
import com.cloud.api.dto.Recommend;
import com.cloud.recommend.domain.RecommendEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class MapperTests {
    private RecommendMapper mapper = Mappers.getMapper(RecommendMapper.class);

    @Test
    public void mapperTests(){

        Assertions.assertNotNull(mapper);

        Recommend dto = new Recommend(1,1,"a","a");

        RecommendEntity entity = mapper.dtoToEntity(dto);

        Assertions.assertEquals(dto.getProductId(),entity.getProductId());
        Assertions.assertEquals(dto.getRecommendId(),entity.getRecommendId());
        Assertions.assertEquals(dto.getAuthor(),entity.getAuthor());
        Assertions.assertEquals(dto.getContent(),entity.getContent());

        Recommend dto2 = mapper.entityToDto(entity);

        Assertions.assertEquals(dto.getProductId(),dto2.getProductId());
        Assertions.assertEquals(dto.getRecommendId(),dto2.getRecommendId());
        Assertions.assertEquals(dto.getAuthor(),dto2.getAuthor());
        Assertions.assertEquals(dto.getContent(),dto2.getContent());
    }
}
