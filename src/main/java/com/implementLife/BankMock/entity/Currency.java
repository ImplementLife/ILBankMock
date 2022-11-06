package com.implementLife.BankMock.entity;

public class Currency {
    private Long id;

    private String name;
    private String code;
    private String codeL;

    public Currency(String codeL) {
        this.codeL = codeL;
    }

    public String getCodeL() {
        return codeL;
    }
    public void setCodeL(String codeL) {
        this.codeL = codeL;
    }
}
