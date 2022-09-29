package com.example.sb101.service;

import com.example.sb101.domain.Contact;
import com.example.sb101.domain.ContactRepository;
import com.example.sb101.mapper.ContactMapper;
import com.example.sb101.web.dto.request.ContactReqDto;
import com.example.sb101.web.dto.response.ContactRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContactService {

    private final ContactMapper contactMapper;

    private final ContactRepository contactRepository;

    public ContactRespDto saveContact(ContactReqDto dto) {
        return contactRepository.save(contactMapper.toEntity(dto)).toDto();
    }

    public ContactRespDto getContactById(Long id) {
        return contactRepository.findById(id).map(contactMapper::toDto).orElse(new ContactRespDto());
    }

    public List<ContactRespDto> getContactList() {
        List<Contact> dtos = contactRepository.findAll();
        return contactMapper.toDtoList(dtos);
    }
}
