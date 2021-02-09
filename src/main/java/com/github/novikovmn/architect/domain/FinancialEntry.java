package com.github.novikovmn.architect.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "financial_entries")
public class FinancialEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "money", referencedColumnName = "id")
    private Money money;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "note")
    private String note;

    @Override
    public String toString() {
        return "FinancialEntry(id=" + id + ", date=" + date.toString()
                + ", money=" + money.toString() + ", category=" + category.toString()
                + ", note=" + note + ")";
    }
}
