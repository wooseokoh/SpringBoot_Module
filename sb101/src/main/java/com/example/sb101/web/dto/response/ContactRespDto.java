package com.example.sb101.web.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactRespDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
}
