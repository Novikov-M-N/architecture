package com.github.novikovmn.architect.utils;

import com.github.novikovmn.architect.domain.Category;
import com.github.novikovmn.architect.domain.FinancialEntry;

import java.util.Date;

/**
 * Класс-обёртка для создания записи финансовой отчётности. Добавляет функционал логирования.
 */
public class LoggedFinancialEntryBuilder implements FinancialEntryBuilder{

    /**
     * Экземпляр простого построителя записей финансовой отчётности
     */
    private final SimpleFinancialEntryBuilder builder = new SimpleFinancialEntryBuilder();

    // Сеттеры полей, требуемых для создания записи - делегируются простому построителю.
    @Override
    public void setDate(Date date) { builder.setDate(date); }
    @Override
    public void setAmount(int amount) { builder.setAmount(amount); }
    @Override
    public void setCategory(Category category) { builder.setCategory(category); }
    @Override
    public void setNote(String note) { builder.setNote(note); }

    /**
     * Функция построения записи финансовой отчётности плюс логирование с выводом длительности совершения операции
     * @return Новая запись на базе введённых данных
     * @throws Exception Если не все требуемые данные были введены
     */
    @Override
    public FinancialEntry getEntry() throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("Building new Financial Entry...");
        FinancialEntry entry = builder.getEntry();
        System.out.printf("Financial entry was build for %d ms", System.currentTimeMillis() - startTime);
        System.out.println();
        return entry;
    }
}
