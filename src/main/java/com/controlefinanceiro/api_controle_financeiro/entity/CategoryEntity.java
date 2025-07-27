package com.controlefinanceiro.api_controle_financeiro.entity;

import com.controlefinanceiro.api_controle_financeiro.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "tbl_categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"userEntity", "transactionEntities"})
@EqualsAndHashCode(of = "id")
public class CategoryEntity {

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
    private UserEntity userEntity;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TransactionEntity> transactionEntities;
}
