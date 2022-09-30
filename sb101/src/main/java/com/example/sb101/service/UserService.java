package com.example.sb101.service;

import com.example.sb101.domain.User;
import com.example.sb101.domain.UserRepository;
import com.example.sb101.mapper.UserMapper;
import com.example.sb101.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public User saveUser(UserDto UserDto) {
        return userRepository.save(userMapper.toEntity(UserDto));
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO )
                .orElse(new UserDto());
    }

    public List<UserDto> getUserList() {
        List<User> users = 	userRepository.findAll();
        return  userMapper.toDTOList(users);
    }

}