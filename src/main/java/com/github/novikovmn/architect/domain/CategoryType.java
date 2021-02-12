package com.github.novikovmn.architect.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "category_types")
public class CategoryType{
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    public String toString() {
        return "CategoryType(id=" + id + ", title=" + title + ")";
    }
}
