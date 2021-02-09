package com.github.novikovmn.architect.utils;

import com.github.novikovmn.architect.domain.Category;
import com.github.novikovmn.architect.domain.FinancialEntry;
import com.github.novikovmn.architect.domain.Money;
import com.github.novikovmn.architect.repository.FinancialEntryRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Простой построитель записей финансовой отчётности
 */
@Setter
public class SimpleFinancialEntryBuilder implements FinancialEntryBuilder {

    //Данные, требуемые для построения новой записи
    private Date date;
    private Money money;
    private Category category;
    private String note;

    /**
     * Функция создания записи. Проверяет наличие всех требуемых данных
     * @return Новая запись
     * @throws Exception Если не все требуемые данные были введены
     */
    @Override
    public FinancialEntry getEntry() throws Exception {
        if (date == null) { throw new Exception("Дата не указана"); }
        if (money == null) { throw new Exception("Сумма не указана"); }
        if (category == null) { throw new Exception("Категория не указана"); }
        // Примечание не является обязательным полем. Если оно не указано, оно создаётся пустым.
        if (note == null) { note = ""; }
        FinancialEntry financialEntry = new FinancialEntry();
        financialEntry.setDate(date);
        financialEntry.setMoney(money);
        financialEntry.setCategory(category);
        financialEntry.setNote(note);
        return financialEntry;
    }
}
