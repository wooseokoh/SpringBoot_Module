package com.example.sb101.web.dto.request;

import com.example.sb101.domain.Contact;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ContactReqDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;

    public Contact toEntity() {
        return Contact.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNo(phoneNo)
                .build();
    }
}