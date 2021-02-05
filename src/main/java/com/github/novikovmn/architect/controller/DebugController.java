package com.github.novikovmn.architect.controller;

import com.github.novikovmn.architect.domain.Currency;
import com.github.novikovmn.architect.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({"debug","debug/"})
public class DebugController {
    @Autowired
    private CurrencyRepository currencyRepository;

    @GetMapping({"currency", "currency/"})
    public void currencyList() {
        currencyRepository.findAll().stream().forEach(System.out::println);
    }
}
