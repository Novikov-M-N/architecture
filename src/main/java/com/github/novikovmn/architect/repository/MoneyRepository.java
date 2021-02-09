package com.github.novikovmn.architect.repository;

import com.github.novikovmn.architect.domain.Money;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MoneyRepository extends CrudRepository<Money, Long> {
    List<Money> findAll();
}
