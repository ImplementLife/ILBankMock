package com.implementLife.BankMock.controller.dto;

import javax.validation.constraints.NotBlank;

public class PayBillingRequest {
    @NotBlank(message = "Can't be blank")
    public String billingId;
    @NotBlank(message = "Can't be blank")
    public String bankAccountId;

    public PayBillingRequest() {
    }

    public String getBillingId() {
        return billingId;
    }

    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
