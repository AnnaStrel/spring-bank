package ru.sgu.springbank.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "transfer_seq")
    @SequenceGenerator(name = "transfer_seq", sequenceName = "transfer_seq")
    private Long id;

    @ManyToOne
    private Account from;

    @ManyToOne
    private Account to;

    @Column
    private BigDecimal amount;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column
    private AccCodeType accCodeType;

    @Column
    private BigDecimal balanceBefore;

    @Column
    private BigDecimal balanceAfter;

    public Transfer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AccCodeType getAccCodeType() {
        return accCodeType;
    }

    public void setAccCodeType(AccCodeType accCodeType) {
        this.accCodeType = accCodeType;
    }

    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

}
