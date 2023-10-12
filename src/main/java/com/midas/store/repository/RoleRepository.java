package com.midas.store.repository;

import com.midas.store.enums.RoleEnum;
import com.midas.store.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {


    RoleEntity findByName(RoleEnum roleEnum);
}
