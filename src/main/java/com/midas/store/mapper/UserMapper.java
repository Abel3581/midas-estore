package com.midas.store.mapper;

import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.response.UserResponse;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public List<UserResponse> mapToUserEntityList(List<UserEntity> users) {
        return users.stream()
                .map(user -> mapToUserResponse(user))
                .collect(Collectors.toList());
    }

    public UserResponse mapToUserResponse(UserEntity user) {
        return UserResponse.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .dni(user.getDni())
                .address(user.getAddress())
                .username(user.getUsername())
                .id(user.getId())

                .build();
    }
}
