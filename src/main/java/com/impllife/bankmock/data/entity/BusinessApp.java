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
public class BusinessApp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private UUID accessApiToken;
    private String ibanReceiver;
    private String name;
    private String urlSendResult;
    private boolean needSendResult;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
