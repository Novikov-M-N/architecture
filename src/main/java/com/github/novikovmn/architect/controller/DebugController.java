package com.github.novikovmn.architect.controller;

import com.github.novikovmn.architect.domain.Currency;
import com.github.novikovmn.architect.repository.CategoryRepository;
import com.github.novikovmn.architect.repository.CurrencyRepository;
import com.github.novikovmn.architect.utils.FinancialEntryBuilder;
import com.github.novikovmn.architect.utils.LoggedFinancialEntryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping({"debug","debug/"})
public class DebugController {
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping({"currency", "currency/"})
    public void currencyList() {
        currencyRepository.findAll().stream().forEach(System.out::println);
    }

    /**
     * Проверка создания новой логируемой финансовой записи
     */
    @GetMapping({"entry","entry/"})
    public void newLoggedEntry() {
        FinancialEntryBuilder builder = new LoggedFinancialEntryBuilder();
        builder.setDate(new Date());
        builder.setAmount(200);
        builder.setCategory(categoryRepository.findById(2).get());
        builder.setNote("Тестовая логируемая запись");
        try {
            System.out.println(builder.getEntry().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
