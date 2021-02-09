package com.github.novikovmn.architect.repository;

import com.github.novikovmn.architect.domain.FinancialEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FinancialEntryRepository extends CrudRepository<FinancialEntry, Long> {
    List<FinancialEntry> findAll();
}
