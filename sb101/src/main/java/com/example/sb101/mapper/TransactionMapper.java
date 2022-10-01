package com.example.sb101.mapper;

import com.example.sb101.web.dto.TransactionDto;
import com.example.sb101.web.dto.TransactionViewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {


    @ValueMapping(source = "NETBANKING", target = "NET_BANKING")
    // @ValueMapping(source = "CREDIT_CARD", target = "CARD")
    // @ValueMapping(source = "DEBIT_CARD", target = "CARD")
     @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "CARD") // 디폴트 카드로
//    @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = "CARD")   // 넷뱅킹, 디폴트 카드 빼고 나머지 제거(매핑 안함)
    @Mapping(source = "paymentType", target ="paymentViewType" )
    TransactionViewDto toViewDTO(TransactionDto transactionDto);

}