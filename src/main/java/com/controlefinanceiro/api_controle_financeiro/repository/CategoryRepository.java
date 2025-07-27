package com.controlefinanceiro.api_controle_financeiro.repository;

import com.controlefinanceiro.api_controle_financeiro.entity.CategoryEntity;
import com.controlefinanceiro.api_controle_financeiro.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findByUserEntity(UserEntity userEntity);
}
