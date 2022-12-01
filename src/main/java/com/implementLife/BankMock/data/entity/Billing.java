package com.implementLife.BankMock.data.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String receiverName;
    private String paymentDescription;
    private String completePaymentLink;
    @ManyToOne
    @JoinColumn(name = "bank_account_receiver_id")
    private BankAccount bankAccountReceiver;
    private Date lastDateTimeForPay;
    private BillingStatus status;
    private String sum;

    @ManyToOne
    @JoinColumn(name = "business_app_id")
    private BusinessApp businessApp;

    public BusinessApp getBusinessApp() {
        return businessApp;
    }
    public void setBusinessApp(BusinessApp businessApp) {
        this.businessApp = businessApp;
    }

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
