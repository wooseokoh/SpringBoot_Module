package com.example.sb115.dto.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.sb115.domain.user.User;
import com.example.sb115.domain.user.UserEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.Setter;

public class UserReqDto {
    @Setter
    @Getter
    public static class JoinReqDto {

        @NotEmpty(message = "username은 필수입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2~20자 이내로 작성해주세요.")
        private String username;

        @NotEmpty(message = "password는 필수입니다")
        @Size(min = 4, max = 20)
        private String password;

        @NotEmpty(message = "email은 필수입니다")
        @Size(min = 9, max = 20)
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 적어주세요")
        private String email;

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .role(UserEnum.USER)
                    .build();
        }
    }
}
