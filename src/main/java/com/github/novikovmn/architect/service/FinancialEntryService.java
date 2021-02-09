package com.github.novikovmn.architect.service;

import com.github.novikovmn.architect.domain.FinancialEntry;
import com.github.novikovmn.architect.repository.FinancialEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialEntryService {
    @Autowired
    private FinancialEntryRepository financialEntryRepository;

    public void save(FinancialEntry financialEntry) { financialEntryRepository.save(financialEntry); }

    public List<FinancialEntry> findAll() { return financialEntryRepository.findAll(); }

    public FinancialEntry findById(Long id) { return financialEntryRepository.findById(id).get(); }

    public void delete(FinancialEntry financialEntry) { financialEntryRepository.delete(financialEntry); }
}
