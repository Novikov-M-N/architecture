package com.github.novikovmn.architect.controller;

import com.github.novikovmn.architect.domain.Currency;
import com.github.novikovmn.architect.domain.FinancialEntry;
import com.github.novikovmn.architect.domain.Money;
import com.github.novikovmn.architect.repository.CategoryRepository;
import com.github.novikovmn.architect.repository.CurrencyRepository;
import com.github.novikovmn.architect.repository.FinancialEntryRepository;
import com.github.novikovmn.architect.repository.MoneyRepository;
import com.github.novikovmn.architect.utils.FinancialEntryBuilder;
import com.github.novikovmn.architect.utils.LoggedFinancialEntryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Временный контроллер для проверки и отладки работоспособности элементов системы
 */
@RestController
@RequestMapping({"debug","debug/"})
public class DebugController {
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MoneyRepository moneyRepository;
    @Autowired
    private FinancialEntryRepository financialEntryRepository;

    // Проверка корректного взаимодействия с сущностью типа "валюта" в БД.
    @GetMapping({"currency", "currency/"})
    public void currencyList() {
        currencyRepository.findAll().stream().forEach(System.out::println);
    }

    // Проверка создания новой логируемой финансовой записи
    @GetMapping({"entry","entry/"})
    public void newLoggedEntry() {
        Long entryId;
        FinancialEntryBuilder builder = new LoggedFinancialEntryBuilder();
        builder.setDate(new Date());
        Money money = new Money();
        money.setAmount(BigDecimal.valueOf(200.50));
        money.setCurrency(currencyRepository.findByCharCode("RUR"));
        moneyRepository.save(money);
        builder.setMoney(money);
        builder.setCategory(categoryRepository.findById(2).get());
        builder.setNote("Тестовая логируемая запись");
        try {
            FinancialEntry entry = builder.getEntry();
            financialEntryRepository.save(entry);
            entryId = entry.getId();
            System.out.println(entry);
            entry.getMoney().toCurrency(currencyRepository.findByCharCode("USD"));
            System.out.println(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Проверка корректного взаимодействия с сущностью типа "Денежная сумма" в БД.
    @GetMapping({"money","money/"})
    public void showMeTheMoney() {
        List<Money> moneyList = moneyRepository.findAll();
        List<Currency> currencies = currencyRepository.findAll();
        for (Money money: moneyList) {
            System.out.println(money);
            for (Currency currency: currencies) {
                System.out.println(money.toCurrency(currency));
            }
            System.out.println();
        }
    }
}
