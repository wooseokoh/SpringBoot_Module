package com.example.sb101.web.dto.request.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ContactListResDto {
    List<ContactRespDto> items;

    @Builder
    public ContactListResDto(List<ContactRespDto> contactList) {
        this.items = contactList;
    }
}
