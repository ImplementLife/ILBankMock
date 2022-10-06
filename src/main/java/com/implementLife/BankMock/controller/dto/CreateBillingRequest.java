package com.implementLife.BankMock.controller.dto;

import javax.validation.constraints.NotBlank;

public class CreateBillingRequest {
    @NotBlank(message = "Can't be blank")
    public String units;
    @NotBlank(message = "Can't be blank")
    public String bankAccountReceiverId;

    public CreateBillingRequest() {
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getBankAccountReceiverId() {
        return bankAccountReceiverId;
    }

    public void setBankAccountReceiverId(String bankAccountReceiverId) {
        this.bankAccountReceiverId = bankAccountReceiverId;
    }
}
