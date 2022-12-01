package com.implementLife.BankMock.data.dto;

public class NotifyMessage {
    private String id;
    private String accToken;
    private String res;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAccToken() {
        return accToken;
    }
    public void setAccToken(String accToken) {
        this.accToken = accToken;
    }

    public String getRes() {
        return res;
    }
    public void setRes(String res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "{" +
            "id='" + id + '\'' +
            ", accToken='" + accToken + '\'' +
            ", res='" + res + '\'' +
            '}';
    }
}
