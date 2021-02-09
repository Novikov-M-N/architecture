package com.github.novikovmn.architect.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "currencies")
public class Currency {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "charcode")
    private String charCode;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "rate")
    private BigDecimal rate;

    @Override
    public String toString() {
        return "Currency(id=" + id + ", charCode=" + charCode + ", symbol=" + symbol + ")";
    }
}
