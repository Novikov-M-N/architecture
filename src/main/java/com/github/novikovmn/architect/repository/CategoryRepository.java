package com.github.novikovmn.architect.repository;

import com.github.novikovmn.architect.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAll();
}
