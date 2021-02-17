package com.github.novikovmn.architect.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category extends Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type")
    private CategoryType type;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<FinancialEntry> financialEntries;

    @Override
    public String toString() {
        return "Category(id=" + id + ", title=" + title + ", type=" + type + ")";
    }
}
