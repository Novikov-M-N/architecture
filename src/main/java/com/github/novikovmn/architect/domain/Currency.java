package com.github.novikovmn.architect.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "currencies")
public class Currency extends Domain{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "char_code")
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
