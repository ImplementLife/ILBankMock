package com.impllife.bankmock.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountCreateOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private String status;  // dismiss, accept, onReview
    private String comment; // comment for status

    @ManyToOne
    @JoinColumn(name = "template_id")
    private BankAccountTemplate template;

}
