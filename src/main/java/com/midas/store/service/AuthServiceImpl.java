package com.midas.store.service;

import com.midas.store.enums.RoleEnum;
import com.midas.store.mapper.AuthMapper;
import com.midas.store.model.entity.RoleEntity;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.request.RegisterRequest;
import com.midas.store.model.response.RegisterResponse;
import com.midas.store.repository.RoleRepository;
import com.midas.store.repository.UserRepository;
import com.midas.store.security.jwt.JwtUtil;
import com.midas.store.service.injectionDependency.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest request) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(request.getUsername());
        if(userEntity.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El Cliente ya esta registrado");

        }
        UserEntity user = authMapper.mapToRegisterRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of((RoleEntity) roleRepository.findByName(RoleEnum.CUSTOMER)));
        UserEntity userCreate = userRepository.save(user);
        RegisterResponse response = authMapper.mapToRegisterResponse(userCreate);
        response.setToken(jwtUtil.generate(request.getUsername()));
        return response;
    }
}
