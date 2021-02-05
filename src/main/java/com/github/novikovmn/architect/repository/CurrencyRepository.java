package com.github.novikovmn.architect.repository;

import com.github.novikovmn.architect.domain.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrencyRepository extends CrudRepository<Currency, String> {
    List<Currency> findAll();
}
