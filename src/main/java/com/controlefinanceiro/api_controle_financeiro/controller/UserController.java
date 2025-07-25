package com.controlefinanceiro.api_controle_financeiro.controller;


import com.controlefinanceiro.api_controle_financeiro.dto.request.UserCreatedRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.request.UserRequest;
import com.controlefinanceiro.api_controle_financeiro.dto.response.UserResponse;
import com.controlefinanceiro.api_controle_financeiro.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody @Valid UserCreatedRequest request) {
        return ResponseEntity.ok(service.updateUser(id, request));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(service.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

}
