package com.github.novikovmn.architect.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "currencies")
public class Currency {

    @Id
    @Column(name = "charcode")
    private String charCode;
    @Column(name = "symbol")
    private String symbol;

    @Override
    public String toString() {
        return "Currency(charCode=" + charCode + ", symbol=" + symbol + ")";
    }
}
