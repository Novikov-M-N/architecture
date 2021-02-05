package com.github.novikovmn.architect.controller;

import com.github.novikovmn.architect.repository.CategoryRepository;
import com.github.novikovmn.architect.utils.SimpleFinancialEntryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping({"entry", "entry/"})
public class FinancialEntryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping({"", "/"})
    public void newEntry() {
        SimpleFinancialEntryBuilder builder = new SimpleFinancialEntryBuilder();
        builder.setDate(new Date());
        builder.setAmount(100);
        builder.setCategory(categoryRepository.findById(1).get());
        builder.setNote("Тестовая запись");
        try {
            System.out.println(builder.getEntry().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
