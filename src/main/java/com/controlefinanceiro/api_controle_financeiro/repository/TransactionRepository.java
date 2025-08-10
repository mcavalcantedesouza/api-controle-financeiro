package com.controlefinanceiro.api_controle_financeiro.repository;

import com.controlefinanceiro.api_controle_financeiro.entity.TransactionEntity;
import com.controlefinanceiro.api_controle_financeiro.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    List<TransactionEntity> findByUserEntity(UserEntity userEntity);

    Optional<TransactionEntity> findByIdAndUserEntity(Integer id, UserEntity userEntity);

    boolean existsByIdAndUserEntity(Integer id, UserEntity userEntity);
}