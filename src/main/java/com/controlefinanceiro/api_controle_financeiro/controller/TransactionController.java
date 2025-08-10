package com.controlefinanceiro.api_controle_financeiro.controller;

import com.controlefinanceiro.api_controle_financeiro.dto.request.TransactionRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.response.TransactionResponse;
import com.controlefinanceiro.api_controle_financeiro.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(request));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactionsForUser());
    }

    @GetMapping("/{id}")

    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Integer id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PutMapping("/{id}")

    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable Integer id, @RequestBody @Valid TransactionRequest request) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, request));
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteTransaction(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}