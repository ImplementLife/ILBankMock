package com.implementLife.BankMock.controller.dto;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class CreateBillingRequest {
    @NotBlank(message = "Can't be blank")
    public String units;
    @NotBlank(message = "Can't be blank")
    public String iban;
    @NotBlank(message = "Can't be blank")
    public UUID accessToken;

    public CreateBillingRequest() {}

    public UUID getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(UUID accessToken) {
        this.accessToken = accessToken;
    }

    public String getUnits() {
        return units;
    }
    public void setUnits(String units) {
        this.units = units;
    }

    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }
}
