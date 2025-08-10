package com.controlefinanceiro.api_controle_financeiro.service;

import com.controlefinanceiro.api_controle_financeiro.dto.request.TransactionRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.response.TransactionResponse;
import com.controlefinanceiro.api_controle_financeiro.entity.CategoryEntity;
import com.controlefinanceiro.api_controle_financeiro.entity.TransactionEntity;
import com.controlefinanceiro.api_controle_financeiro.entity.UserEntity;
import com.controlefinanceiro.api_controle_financeiro.exception.ResourceNotFoundException;
import com.controlefinanceiro.api_controle_financeiro.mapper.TransactionMapper;
import com.controlefinanceiro.api_controle_financeiro.repository.CategoryRepository;
import com.controlefinanceiro.api_controle_financeiro.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {
        UserEntity user = userService.getAuthenticatedUser();
        CategoryEntity category = findCategoryByIdAndValidateOwnership(request.getCategoryId(), user);

        if (request.getValueType() != category.getValueType()) {
            throw new IllegalArgumentException("O tipo da transação (" + request.getValueType() +
                    ") não corresponde ao tipo da categoria (" + category.getValueType() + ").");
        }

        TransactionEntity transactionEntity = transactionMapper.toEntity(request);
        transactionEntity.setUserEntity(user);
        transactionEntity.setCategoryEntity(category);

        TransactionEntity savedTransaction = transactionRepository.save(transactionEntity);
        return transactionMapper.transactionEntityToTransactionResponse(savedTransaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getAllTransactionsForUser() {
        UserEntity user = userService.getAuthenticatedUser();

        List<TransactionEntity> transactions = transactionRepository.findByUserEntity(user);
        return transactionMapper.transactionEntityListToTransactionResponseList(transactions);
    }

    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(Integer id) {
        UserEntity user = userService.getAuthenticatedUser();

        TransactionEntity transactionEntity = transactionRepository.findByIdAndUserEntity(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada ou não pertence ao usuário."));
        return transactionMapper.transactionEntityToTransactionResponse(transactionEntity);
    }

    @Transactional
    public TransactionResponse updateTransaction(Integer id, TransactionRequest request) {
        UserEntity user = userService.getAuthenticatedUser();

        TransactionEntity transactionEntity = transactionRepository.findByIdAndUserEntity(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada ou não pertence ao usuário."));

        CategoryEntity category = findCategoryByIdAndValidateOwnership(request.getCategoryId(), user);

        transactionMapper.updateEntity(request, transactionEntity);
        transactionEntity.setCategoryEntity(category);

        TransactionEntity updatedTransaction = transactionRepository.save(transactionEntity);
        return transactionMapper.transactionEntityToTransactionResponse(updatedTransaction);
    }

    @Transactional
    public void deleteTransaction(Integer id) {
        UserEntity user = userService.getAuthenticatedUser();

        if (!transactionRepository.existsByIdAndUserEntity(id, user)) {
            throw new ResourceNotFoundException("Transação não encontrada ou não pertence ao usuário.");
        }
        transactionRepository.deleteById(id);
    }

    private CategoryEntity findCategoryByIdAndValidateOwnership(Integer categoryId, UserEntity user) {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + categoryId));

        if (!category.getUserEntity().getId().equals(user.getId())) {
            throw new AccessDeniedException("Acesso negado. A categoria não pertence ao usuário.");
        }
        return category;
    }
}