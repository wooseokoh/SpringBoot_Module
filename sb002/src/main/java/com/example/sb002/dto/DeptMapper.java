package com.example.sb002.dto;

import com.example.sb002.domain.DeptEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DeptMapper {

    @Mappings({
            @Mapping(target = "dtoValue", ignore = true) // 타켓에 해당 필드 무시
    })
    Dept entityToDto(DeptEntity deptEntity);

    @Mappings({
            @Mapping(target = "entityValue", ignore = true)  // 타켓에 해당 필드 무시
    })
    DeptEntity dtoToEntity(Dept dept);
}