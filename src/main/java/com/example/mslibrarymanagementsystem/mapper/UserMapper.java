package com.example.mslibrarymanagementsystem.mapper;

import com.example.mslibrarymanagementsystem.dto.request.RegisterRequest;
import com.example.mslibrarymanagementsystem.entity.UserEntity;
import org.springframework.stereotype.Component;

import static com.example.mslibrarymanagementsystem.enums.Role.USER;

@Component
public class UserMapper {
    public UserEntity toEntity(RegisterRequest registerRequest) {
        return UserEntity.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .surname(registerRequest.getSurname())
                .phoneNumber(registerRequest.getPhoneNumber())
                .role(USER)
                .enabled(true)
                .build();
    }
}