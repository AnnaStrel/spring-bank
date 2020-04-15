package ru.sgu.springbank.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private User user;

    @Column
    private BigDecimal amount;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private AccCodeType accCodeType;

    public Account() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccCodeType getAccCodeType() {
        return accCodeType;
    }

    public void setAccCodeType(AccCodeType accCodeType) {
        this.accCodeType = accCodeType;
    }

}
