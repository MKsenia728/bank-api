package com.oksanamolchanova.bank.api.entity;

import com.oksanamolchanova.bank.api.entity.enums.CurrencyType;
import com.oksanamolchanova.bank.api.entity.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "currency_code")
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyCode;

    @Column(name = "interest_rate")
    private Float interestRate;

    @Column(name = "limit")
    private Integer limit;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Agreement> agreement;

    @ManyToMany
    @JoinTable(name = "products_managers",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "manager_id")})
    private Set<Manager> managers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Float.compare(product.interestRate, interestRate) == 0 && Objects.equals(name, product.name) && status == product.status && currencyCode == product.currencyCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status, currencyCode, interestRate);
    }
}
