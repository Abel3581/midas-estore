package com.midas.store.service;

import com.midas.store.mapper.AuthMapper;
import com.midas.store.model.request.RegisterRequest;
import com.midas.store.model.response.RegisterResponse;
import com.midas.store.repository.UserRepository;
import com.midas.store.security.jwt.JwtUtil;
import com.midas.store.service.injectionDependency.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest request) {

        return null;
    }
}
