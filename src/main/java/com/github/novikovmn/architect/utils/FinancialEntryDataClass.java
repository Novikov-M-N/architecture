package com.github.novikovmn.architect.utils;

import com.github.novikovmn.architect.domain.Category;
import com.github.novikovmn.architect.domain.Money;
import lombok.Getter;

import java.util.Date;

/**
 * Типа UI. Имитирует передачу данных в бизнес-логику через пользовательский интерфейс.
 */
@Getter
public class FinancialEntryDataClass {
    private final Date date;
    private final Money money;
    private final Category category;
    private final String note;

    public FinancialEntryDataClass(Date date, Money money, Category category, String note) {
        this.date = date;
        this.money = money;
        this.category = category;
        this.note = note;
    }
}
