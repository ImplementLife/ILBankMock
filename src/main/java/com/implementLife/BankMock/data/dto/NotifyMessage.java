package com.implementLife.BankMock.data.dto;

public class NotifyMessage {
    private String id;
    private String accToken;
    private String status;

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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
            "id='" + id + '\'' +
            ", accToken='" + accToken + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}
