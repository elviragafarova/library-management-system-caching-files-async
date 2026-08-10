package com.example.mslibrarymanagementsystem.service;

import com.example.mslibrarymanagementsystem.dto.request.LoginRequest;
import com.example.mslibrarymanagementsystem.dto.request.RegisterRequest;
import com.example.mslibrarymanagementsystem.dto.response.JwtResponse;
import com.example.mslibrarymanagementsystem.exceptions.EmailAlreadyExistsException;
import com.example.mslibrarymanagementsystem.exceptions.InvalidCredentialsException;
import com.example.mslibrarymanagementsystem.mapper.UserMapper;
import com.example.mslibrarymanagementsystem.repository.UserRepository;
import com.example.mslibrarymanagementsystem.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public JwtResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email already exists: " + registerRequest.getEmail());
        }
        var user = userMapper.toEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        var savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return JwtResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }

    public JwtResponse login(LoginRequest loginRequest) {
        var user = userRepository.findByEmailIgnoreCase(loginRequest.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid credentials"
                        ));

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        )) {

            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return JwtResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }
}