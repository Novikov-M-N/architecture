package com.github.novikovmn.architect.utils.commands;

import com.github.novikovmn.architect.service.FinancialEntryService;
import com.github.novikovmn.architect.utils.FinancialEntryBuilder;
import com.github.novikovmn.architect.utils.FinancialEntryDataClass;
import com.github.novikovmn.architect.utils.LoggedFinancialEntryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class CreateFinancialEntryCommand implements Command{
    private final FinancialEntryDataClass template;

    private final FinancialEntryService financialEntryService;

    public CreateFinancialEntryCommand(FinancialEntryDataClass template, FinancialEntryService financialEntryService) {
        this.template = template;
        this.financialEntryService = financialEntryService;
    }


    @Override
    public void execute() {
        FinancialEntryBuilder builder = new LoggedFinancialEntryBuilder();
        builder.setDate(template.getDate());
        builder.setMoney(template.getMoney());
        builder.setCategory(template.getCategory());
        builder.setNote(template.getNote());
        try {
            financialEntryService.save(builder.getEntry());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
