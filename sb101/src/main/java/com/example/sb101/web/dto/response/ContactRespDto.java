package com.example.sb101.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContactRespDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;

    @Builder
    public ContactRespDto(Long id, String firstName, String lastName, String email, String phoneNo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
    }
}
