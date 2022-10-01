package com.example.sb101.controller;

import com.example.sb101.domain.Contact;
import com.example.sb101.service.ContactService;
import com.example.sb101.web.dto.response.CMRespDto;
import com.example.sb101.web.dto.response.ContactRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/contact")
    public ResponseEntity<?> saveContact(@RequestBody ContactRespDto contactDTO) {
        Contact contactRespDto = contactService.saveContact(contactDTO);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("Contact 저장 성공").body(contactRespDto).build(), HttpStatus.OK);
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<?> getContactById(@PathVariable Long id) {
        ContactRespDto contactRespDto = contactService.getContactById(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("Contact 한건보기 성공").body(contactRespDto).build(), HttpStatus.OK);
    }

    @GetMapping("/contacts")
    public ResponseEntity<?> getContactList() {
        List<ContactRespDto> contactListResDto = contactService.getContactList();
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("Contact 목록보기 성공").body(contactListResDto).build(), HttpStatus.OK);
    }
}