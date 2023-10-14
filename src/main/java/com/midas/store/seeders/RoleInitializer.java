package com.midas.store.seeders;

import com.midas.store.enums.RoleEnum;
import com.midas.store.model.entity.RoleEntity;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.repository.RoleRepository;
import com.midas.store.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        // Verificar si los roles ya existen en la base de datos
        if (roleRepository.findByName(RoleEnum.ADMIN) == null) {
            RoleEntity adminRole = new RoleEntity();
            adminRole.setName(RoleEnum.ADMIN);
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByName(RoleEnum.CUSTOMER) == null) {
            RoleEntity customerRole = new RoleEntity();
            customerRole.setName(RoleEnum.CUSTOMER);
            roleRepository.save(customerRole);
        }
        if((userRepository.findAll().isEmpty())){
            UserEntity user = new UserEntity();
            user.setName("Abel");
            user.setLastname("Acevedo");
            user.setAddress("Garin");
            user.setPassword(passwordEncoder.encode("12345678"));
            user.setDni("1234567890");
            user.setUsername("admin@gmail.com");
            user.setRoles(Set.of((RoleEntity) roleRepository.findByName(RoleEnum.ADMIN)));
            userRepository.save(user);

        }
    }


}
