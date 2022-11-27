package com.implementLife.BankMock.data.dto;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

public class CreateBillingRequest {
    @NotBlank(message = "Can't be blank")
    private String units;
    @NotBlank(message = "Can't be blank")
    private UUID accessToken;
    @NotBlank(message = "Can't be blank")
    private UUID appId;
    private Date dateCreate;
    private String paymentDescription;
    private String completePaymentLink;

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

    public Date getDateCreate() {
        return dateCreate;
    }
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public UUID getAppId() {
        return appId;
    }
    public void setAppId(UUID appId) {
        this.appId = appId;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }
    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getCompletePaymentLink() {
        return completePaymentLink;
    }
    public void setCompletePaymentLink(String completePaymentLink) {
        this.completePaymentLink = completePaymentLink;
    }
}
