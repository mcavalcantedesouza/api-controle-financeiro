package com.controlefinanceiro.api_controle_financeiro.repository;

import com.controlefinanceiro.api_controle_financeiro.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
}
