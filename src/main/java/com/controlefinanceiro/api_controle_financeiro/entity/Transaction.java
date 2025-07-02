package com.controlefinanceiro.api_controle_financeiro.entity;

import com.controlefinanceiro.api_controle_financeiro.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_transactions")
@Data
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "description", nullable = false)
    private String description ;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Column(name = "valueType", nullable = false)
    private TransactionType valueType;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}