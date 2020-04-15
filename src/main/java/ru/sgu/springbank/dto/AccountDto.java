package ru.sgu.springbank.dto;

import ru.sgu.springbank.entity.AccCodeType;

import java.math.BigDecimal;

public class AccountDto {

    private String id;

    private BigDecimal amount;

    private AccCodeType accCodeType;

    public AccountDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
