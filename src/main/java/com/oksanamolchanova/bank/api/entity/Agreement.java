package com.oksanamolchanova.bank.api.entity;

import com.oksanamolchanova.bank.api.entity.enums.AccountProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "agreements")
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable=false, nullable=false)
    private Integer id;

    @Column(name = "interest_rate")
    private Float interestRate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountProductStatus status;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "created_at", updatable=false, nullable=false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(optional = false)
    @JoinColumn(name = "account_id", referencedColumnName="id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName="id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agreement agreement = (Agreement) o;
        return Objects.equals(account, agreement.account) && Objects.equals(product, agreement.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, product);
    }
}
