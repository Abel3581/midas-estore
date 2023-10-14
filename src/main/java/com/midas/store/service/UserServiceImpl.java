package com.midas.store.service;


import com.midas.store.mapper.UserMapper;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.response.UserResponse;
import com.midas.store.repository.UserRepository;
import com.midas.store.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getAllUsers() {
        log.info("Entrando al getAllUsers del servicio");
        List<UserEntity> products = userRepository.findAll();
        List<UserResponse> responses = userMapper.mapToUserEntityList(products);
        log.info(responses);
        return responses;
    }
}
