package com.midas.store.service.injectionDependency;

import com.midas.store.model.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
}
