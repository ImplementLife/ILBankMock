package com.implementLife.BankMock.controller.dto;

import java.util.UUID;

public interface ClientPersonalInfoRequest {
    String getPass();

    UUID getId();

    String getEmail();

    String getFirstName();

    String getLastName();

    String getMiddleName();

    String getPhoneNumber();
}
