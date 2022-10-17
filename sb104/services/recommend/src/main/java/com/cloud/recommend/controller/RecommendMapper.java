package com.cloud.recommend.controller;

import com.cloud.api.dto.Recommend;
import com.cloud.recommend.domain.RecommendEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecommendMapper {
    @Mappings({})
    Recommend entityToDto(RecommendEntity entity);

    @Mappings({
            @Mapping(target ="id", ignore = true),
            @Mapping(target ="version", ignore = true)
    })
    RecommendEntity dtoToEntity(Recommend dto);

    List<Recommend> entityListToApiList(List<RecommendEntity> entity);
    List<RecommendEntity> dtoListToEntityList(List<Recommend> dto);
}
