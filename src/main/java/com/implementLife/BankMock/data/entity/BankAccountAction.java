package com.implementLife.BankMock.data.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
public class BankAccountAction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String sumBefore;
    private String sum;
    private String description;
    private Date date;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getSumBefore() {
        return sumBefore;
    }
    public void setSumBefore(String sumBefore) {
        this.sumBefore = sumBefore;
    }

    public String getSum() {
        return sum;
    }
    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormatsDate() {
        return new SimpleDateFormat().format(getDate());
    }
}
