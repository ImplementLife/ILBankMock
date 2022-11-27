package com.implementLife.BankMock.data.dto;

public class PayRequest {
    private String code16xCurrentClient;
    private String code16xOtherClient;
    private String sum;

    public String getCode16xCurrentClient() {
        return code16xCurrentClient;
    }
    public void setCode16xCurrentClient(String code16xCurrentClient) {
        this.code16xCurrentClient = code16xCurrentClient;
    }

    public String getCode16xOtherClient() {
        return code16xOtherClient;
    }
    public void setCode16xOtherClient(String code16xOtherClient) {
        this.code16xOtherClient = code16xOtherClient;
    }

    public String getSum() {
        return sum;
    }
    public void setSum(String sum) {
        this.sum = sum;
    }
}
