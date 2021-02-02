package com.github.novikovmn.architect.utils;

import com.github.novikovmn.architect.domain.Category;
import com.github.novikovmn.architect.domain.FinancialEntry;
import lombok.Setter;

import java.util.Date;

@Setter
public class FinancialEntryBuilder {
    private Date date;
    private int amount;
    private Category category;
    private String note;

    public FinancialEntry getEntry() throws Exception {
        if (date == null) { throw new Exception("Дата не указана"); }
        if (amount == 0) { throw new Exception("Сумма не указана"); }
        if (category == null) { throw new Exception("Категория не указана"); }
        if (note == null) { note = ""; }
        FinancialEntry financialEntry = new FinancialEntry();
        financialEntry.setDate(date);
        financialEntry.setAmount(amount);
        financialEntry.setCategory(category);
        financialEntry.setNote(note);
        return financialEntry;
    }
}
