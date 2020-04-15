package ru.sgu.springbank.dto;

import ru.sgu.springbank.entity.AccCodeType;

import java.math.BigDecimal;

public class TransferDto {

    private long id;

    private AccountDto from;

    private AccountDto to;

    private BigDecimal amount;

    private String date;

    private AccCodeType accCodeType;

    private BigDecimal balanceBefore;

    private BigDecimal balanceAfter;

    public TransferDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountDto getFrom() {
        return from;
    }

    public void setFrom(AccountDto from) {
        this.from = from;
    }

    public AccountDto getTo() {
        return to;
    }

    public void setTo(AccountDto to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
