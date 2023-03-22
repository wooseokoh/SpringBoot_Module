package com.example.sb115.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.example.sb115.config.dummy.DummyObject;
import com.example.sb115.domain.user.User;
import com.example.sb115.domain.user.UserRepository;
import com.example.sb115.dto.user.UserReqDto.JoinReqDto;
import com.example.sb115.dto.user.UserRespDto.JoinRespDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends DummyObject {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy // real
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void join_test() throws Exception {
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("abcd");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("abcd@gmail.com");

        // stub 1
        when(userRepository.findByUsername(joinReqDto.getUsername())).thenReturn(Optional.empty());

        // stub 2
        User ssar = newMockUser(1L, "abcd");
        when(userRepository.save(any())).thenReturn(ssar);

        // when
        JoinRespDto joinRespDto = userService.join(joinReqDto);

        // then
        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("abcd");
    }
}