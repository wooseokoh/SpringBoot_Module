package com.example.sb115.config.dummy;

import com.example.sb115.domain.user.User;
import com.example.sb115.domain.user.UserEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DummyObject {

    protected User newUser(String username) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        User user = User.builder()
                .username(username)
                .password(encPassword)
                .email(username + "@gmail.com")
                .role(username.equals("admin") ? UserEnum.ADMIN : UserEnum.USER)
                .build();
        return user;
    }

    protected User newMockUser(Long id, String username) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        User user = User.builder()
                .id(id)
                .username(username)
                .password(encPassword)
                .email(username + "@gmail.com")
                .role(username.equals("admin") ? UserEnum.ADMIN : UserEnum.USER)
                .build();
        return user;
    }
}
