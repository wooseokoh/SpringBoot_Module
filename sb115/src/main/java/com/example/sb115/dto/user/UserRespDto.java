package com.example.sb115.dto.user;

import com.example.sb115.domain.user.User;
import lombok.Getter;
import lombok.Setter;

public class UserRespDto {

    @Setter
    @Getter
    public static class JoinRespDto {
        private Long id;
        private String username;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }
    }
}
