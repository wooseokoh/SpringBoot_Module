package com.example.sb101.mapper;

import com.example.sb101.domain.Contact;
import com.example.sb101.web.dto.response.ContactRespDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    Contact toEntity(ContactRespDto contactRespDto);

    ContactRespDto toDto(Contact contact);

    List<ContactRespDto> toDtoList(List<Contact> contacts);
}
