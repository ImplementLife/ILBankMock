package com.impllife.bankmock.data.entity;

import javax.persistence.*;

@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String code;
    private String codeL;

    public Currency() {
    }
    public Currency(String codeL) {
        this.codeL = codeL;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeL() {
        return codeL;
    }
    public void setCodeL(String codeL) {
        this.codeL = codeL;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
