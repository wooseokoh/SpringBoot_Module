package com.cloud.review.controller;

import com.cloud.api.dto.Recommend;
import com.cloud.api.dto.Review;
import com.cloud.review.domain.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mappings({})
    Review entityToDto(ReviewEntity entity);

    @Mappings({
            @Mapping(target ="id", ignore = true),
            @Mapping(target ="version", ignore = true)
    })
    ReviewEntity dtoToEntity(Review dto);

    List<Review> entityListToApiList(List<ReviewEntity> entity);
    List<ReviewEntity> dtoListToEntityList(List<Review> dto);
}
