package com.midas.store.service.injectionDependency;

import com.midas.store.model.request.RegisterRequest;
import com.midas.store.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
}
