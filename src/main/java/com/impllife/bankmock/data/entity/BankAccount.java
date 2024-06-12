package com.impllife.bankmock.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String code16x;
    private String codeCvv;
    private String iban;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    private long sumBanknote;
    private int sumPenny;
    private String name;

    private Date dateCreate;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAccountAction> bankAccountActions;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
