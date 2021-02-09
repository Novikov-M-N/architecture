package com.github.novikovmn.architect.repository;

import com.github.novikovmn.architect.domain.FinancialEntry;
import org.springframework.data.repository.CrudRepository;

public interface FinancialEntryRepository extends CrudRepository<FinancialEntry, Long> {
}
