package com.midas.store.seeders;

import com.midas.store.enums.RoleEnum;
import com.midas.store.model.entity.RoleEntity;
import com.midas.store.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

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
    }
}
