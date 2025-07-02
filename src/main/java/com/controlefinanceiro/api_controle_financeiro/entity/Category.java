package com.controlefinanceiro.api_controle_financeiro.entity;

import com.controlefinanceiro.api_controle_financeiro.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "tbl_categories")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false)
    private TransactionType valueType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> transactions;
}
