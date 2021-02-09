package com.github.novikovmn.architect.utils;

import com.github.novikovmn.architect.domain.Category;
import com.github.novikovmn.architect.domain.FinancialEntry;
import com.github.novikovmn.architect.domain.Money;

import java.util.Date;

/**
 * Общий интерфейс построителя записей финансовой отчётности
 */
public interface FinancialEntryBuilder {

    // Сеттеры для ввода данных, на базе которых будет построена новая запись
    void setDate(Date date);
    void setMoney(Money money);
    void setCategory(Category category);
    void setNote(String note);

    /**
     * Билдер записи финансовой отчётности
     * @return Новая запись
     * @throws Exception Если не все требуемые данные были введены
     */
    FinancialEntry getEntry() throws Exception;
}
