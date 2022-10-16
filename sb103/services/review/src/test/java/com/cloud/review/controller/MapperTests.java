package com.cloud.review.controller;

import com.cloud.api.dto.Recommend;
import com.cloud.api.dto.Review;
import com.cloud.review.domain.ReviewEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

public class MapperTests {
    private ReviewMapper mapper = Mappers.getMapper(ReviewMapper.class);

    @Test
    public void _mapperTests(){

        Assertions.assertNotNull(mapper);

        Review api = new Review(1,1,"author","subject","content");

        ReviewEntity entity = mapper.dtoToEntity(api);

        Assertions.assertEquals(api.getProductId(),entity.getProductId());
        Assertions.assertEquals(api.getReviewId(),entity.getReviewId());
        Assertions.assertEquals(api.getAuthor(),entity.getAuthor());
        Assertions.assertEquals(api.getSubject(),entity.getSubject());
        Assertions.assertEquals(api.getContent(),entity.getContent());

        Review api2 = mapper.entityToDto(entity);

        Assertions.assertEquals(api.getProductId(),api2.getProductId());
        Assertions.assertEquals(api.getReviewId(),api2.getReviewId());
        Assertions.assertEquals(api.getAuthor(),api2.getAuthor());
        Assertions.assertEquals(api.getSubject(),api2.getSubject());
        Assertions.assertEquals(api.getContent(),api2.getContent());
    }

    @Test
    public void _mapperListTests(){

        Assertions.assertNotNull(mapper);

        Review api = new Review(1,1,"author","subject","content");
        List<Review> apiList = Collections.singletonList(api);
        List<ReviewEntity> entityList = mapper.dtoListToEntityList(apiList);
        Assertions.assertEquals(apiList.size(), entityList.size());

        ReviewEntity entity = entityList.get(0);

        Assertions.assertEquals(api.getProductId(),entity.getProductId());
        Assertions.assertEquals(api.getReviewId(),entity.getReviewId());
        Assertions.assertEquals(api.getAuthor(),entity.getAuthor());
        Assertions.assertEquals(api.getSubject(),entity.getSubject());
        Assertions.assertEquals(api.getContent(),entity.getContent());

        List<Review> api2List = mapper.entityListToApiList(entityList);
        Assertions.assertEquals(apiList.size(), api2List.size());
        Review api2 = api2List.get(0);

        Assertions.assertEquals(api.getProductId(),api2.getProductId());
        Assertions.assertEquals(api.getReviewId(),api2.getReviewId());
        Assertions.assertEquals(api.getAuthor(),api2.getAuthor());
        Assertions.assertEquals(api.getSubject(),api2.getSubject());
        Assertions.assertEquals(api.getContent(),api2.getContent());
    }
}
