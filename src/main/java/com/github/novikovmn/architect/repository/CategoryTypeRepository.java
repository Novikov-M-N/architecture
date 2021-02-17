package com.github.novikovmn.architect.repository;

import com.github.novikovmn.architect.domain.CategoryType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryTypeRepository extends CrudRepository<CategoryType, Integer> {
    List<CategoryType> findAll();
}
