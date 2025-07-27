package com.controlefinanceiro.api_controle_financeiro.repository;

import com.controlefinanceiro.api_controle_financeiro.entity.RoleEntity;
import com.controlefinanceiro.api_controle_financeiro.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRoleName(RoleName roleName);
}