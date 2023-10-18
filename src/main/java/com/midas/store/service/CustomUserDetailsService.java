package com.midas.store.service;

import com.midas.store.exception.UserNotFoundException;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
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
            log.error(LOGIN_EXCEPTION_MESSAGE);
            throw new UserNotFoundException(LOGIN_EXCEPTION_MESSAGE);
        }

    }
}
