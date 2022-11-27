package com.implementLife.BankMock.data.entity;

import java.util.Date;
import java.util.UUID;

public class Billing {
    private UUID id;
    private String receiverName;
    private String paymentDescription;
    private String completePaymentLink;
    private BankAccount bankAccountReceiver;
    private Date lastDateTimeForPay;
    private BillingStatus status;
    private String sum;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public BankAccount getBankAccountReceiver() {
        return bankAccountReceiver;
    }
    public void setBankAccountReceiver(BankAccount bankAccountReceiver) {
        this.bankAccountReceiver = bankAccountReceiver;
    }

    public Date getLastDateTimeForPay() {
        return lastDateTimeForPay;
    }
    public void setLastDateTimeForPay(Date lastDateTimeForPay) {
        this.lastDateTimeForPay = lastDateTimeForPay;
    }

    public BillingStatus getStatus() {
        return status;
    }
    public void setStatus(BillingStatus status) {
        this.status = status;
    }

    public String getSum() {
        return sum;
    }
    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getReceiverName() {
        return receiverName;
    }
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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
