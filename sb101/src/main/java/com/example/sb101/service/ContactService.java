package com.example.sb101.service;

import com.example.sb101.domain.Contact;
import com.example.sb101.domain.ContactRepository;
import com.example.sb101.web.dto.request.ContactDto;
import com.example.sb101.web.dto.request.response.ContactListResDto;
import com.example.sb101.web.dto.request.response.ContactRespDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactRespDto saveContact(ContactDto dto) {

        Contact contact = contactRepository.save(dto.toEntity());
        return contact.toDto();

    }

    public ContactRespDto getContactById(Long id) {

        Optional<Contact> contactOp = contactRepository.findById(id);
        if(contactOp.isPresent()) {
            Contact contact = contactOp.get();
            return contact.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    public ContactListResDto getContactList() {
        List<ContactRespDto> dtos = contactRepository.findAll().stream().map(Contact::toDto).collect(Collectors.toList());

        ContactListResDto contactListResDto = ContactListResDto.builder().contactList(dtos).build();
        return contactListResDto;
    }
}
