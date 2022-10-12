package com.implementLife.BankMock.entity;

import com.implementLife.BankMock.controller.dto.ClientPersonalInfoRequest;
import com.implementLife.BankMock.controller.dto.ClientResponse;

import java.util.List;
import java.util.UUID;

public class Client implements ClientPersonalInfoRequest, ClientResponse {
    private UUID id;

    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private String email;

    private String pass;
    private String roles;

    private List<BankAccount> bankAccounts;

    public Client() {
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
