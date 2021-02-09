package com.github.novikovmn.architect.utils.commands;

import com.github.novikovmn.architect.domain.FinancialEntry;
import com.github.novikovmn.architect.service.FinancialEntryService;

public class DeleteFinancialEntryCommand implements Command{
    private final FinancialEntryService financialEntryService;
    private final FinancialEntry entry;

    public DeleteFinancialEntryCommand(FinancialEntry entry, FinancialEntryService financialEntryService) {
        this.entry = entry;
        this.financialEntryService = financialEntryService;
    }

    @Override
    public void execute() {
        financialEntryService.delete(entry);
    }
}
