package com.oksanamolchanova.bank.api.entity;

import com.oksanamolchanova.bank.api.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.UUID)
    @Column(name = "id", updatable=false, nullable=false)
    private UUID id;

    @Column(name = "type", nullable=false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "amount", nullable=false)
    private Double amount;

    @Column(name = "description", nullable=false)
    private String description;

    @Column(name = "created_at", updatable=false, nullable=false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "debit_account_id", referencedColumnName="id")
    private Account debitAccount;

    @ManyToOne
    @JoinColumn(name = "credit_account_id", referencedColumnName="id")
    private Account creditAccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(createdAt, that.createdAt) && Objects.equals(debitAccount, that.debitAccount) && Objects.equals(creditAccount, that.creditAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, createdAt, debitAccount, creditAccount);
    }
}
