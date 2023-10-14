package com.midas.store.service.injectionDependency;

import com.midas.store.model.request.LoginRequest;
import com.midas.store.model.request.RegisterRequest;
import com.midas.store.model.response.LoginResponse;
import com.midas.store.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
