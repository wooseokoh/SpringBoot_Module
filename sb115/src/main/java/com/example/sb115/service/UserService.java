package com.example.sb115.service;

import com.example.sb115.domain.user.User;
import com.example.sb115.domain.user.UserRepository;
import com.example.sb115.dto.user.UserReqDto.JoinReqDto;
import com.example.sb115.dto.user.UserRespDto.JoinRespDto;
import com.example.sb115.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public JoinRespDto join(JoinReqDto joinReqDto) {
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());
        if (userOP.isPresent()) {
            throw new CustomApiException("동일한 username이 존재합니다");
        }

        // 2. 패스워드 인코딩 + DB 저장
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        // dto response
        return new JoinRespDto(userPS);
    }
}
