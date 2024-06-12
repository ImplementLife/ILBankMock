package com.impllife.bankmock.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
