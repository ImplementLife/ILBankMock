package com.implementLife.BankMock.controller.dto;

public class BillingInfo {
    private String paymentPageUrl;
    private String status;

    public String getPaymentPageUrl() {
        return paymentPageUrl;
    }
    public void setPaymentPageUrl(String paymentPageUrl) {
        this.paymentPageUrl = paymentPageUrl;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
