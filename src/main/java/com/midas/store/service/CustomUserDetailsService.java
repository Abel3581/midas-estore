package com.midas.store.service;

import com.midas.store.model.UserEntity;
import com.midas.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private static String LOGIN_EXCEPTION_MESSAGE = "No hay ninguna cuenta asociada con la dirección de correo electrónico.";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return new User(user.get().getUsername(),
                    user.get().getPassword(),
                    user.get().getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).toList());
        }else {
            throw new UsernameNotFoundException(LOGIN_EXCEPTION_MESSAGE);
        }

    }
}
