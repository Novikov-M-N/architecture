package com.github.novikovmn.architect.controller;

import com.github.novikovmn.architect.domain.CategoryType;
import com.github.novikovmn.architect.domain.Currency;
import com.github.novikovmn.architect.domain.FinancialEntry;
import com.github.novikovmn.architect.domain.Money;
import com.github.novikovmn.architect.repository.CategoryRepository;
import com.github.novikovmn.architect.repository.CategoryTypeRepository;
import com.github.novikovmn.architect.repository.CurrencyRepository;
import com.github.novikovmn.architect.repository.MoneyRepository;
import com.github.novikovmn.architect.service.FinancialEntryService;
import com.github.novikovmn.architect.utils.FinancialEntryBuilder;
import com.github.novikovmn.architect.utils.FinancialEntryDataClass;
import com.github.novikovmn.architect.utils.LoggedFinancialEntryBuilder;
import com.github.novikovmn.architect.utils.commands.Command;
import com.github.novikovmn.architect.utils.commands.CreateFinancialEntryCommand;
import com.github.novikovmn.architect.utils.commands.DeleteFinancialEntryCommand;
import com.github.novikovmn.architect.utils.datamapper.CategoryDataMapper;
import com.github.novikovmn.architect.utils.datamapper.CategoryTypeDataMapper;
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
    private FinancialEntryService financialEntryService;
    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

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
            financialEntryService.save(entry);
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

    // Проверка работоспособности команд на создание и удаление записи финансовой отчётности.
    @GetMapping({"command", "command/"})
    public void commandSimulation() {

        // Создаём первую запись через команду
        Money money = new Money();
        money.setAmount(BigDecimal.valueOf(121.48));
        money.setCurrency(currencyRepository.findByCharCode("USD"));
        moneyRepository.save(money);
        FinancialEntryDataClass template = new FinancialEntryDataClass(
                new Date(),
                money,
                categoryRepository.findById(1).get(),
                "Тестовая запись №1, созданная через команду"
        );
        Command createCommand = new CreateFinancialEntryCommand(template, financialEntryService);
        createCommand.execute();

        // Создаём вторую запись через команду
        money = new Money();
        money.setAmount(BigDecimal.valueOf(500));
        money.setCurrency(currencyRepository.findByCharCode("RUR"));
        moneyRepository.save(money);
        template = new FinancialEntryDataClass(
                new Date(),
                money,
                categoryRepository.findById(2).get(),
                "Тестовая запись №2, созданная через команду"
        );
        createCommand = new CreateFinancialEntryCommand(template, financialEntryService);
        createCommand.execute();

        //Убеждаемся, что записи были внесены в БД
        financialEntryService.findAll().stream().forEach(System.out::println);

        //Удаляем первую запись
        FinancialEntry entry = financialEntryService.findById(1L);
        Command deleteCommand = new DeleteFinancialEntryCommand(entry, financialEntryService);
        deleteCommand.execute();

        //Убеждаемся, что запсь была удалена из БД
        financialEntryService.findAll().stream().forEach(System.out::println);
    }

    // Проверка работы дата мапперов
    @GetMapping({"mapper", "mapper/"})
    public void mappers() {
        CategoryTypeDataMapper categoryTypeDataMapper = new CategoryTypeDataMapper();
        categoryTypeDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println(categoryTypeDataMapper.getById(1));

        System.out.println(categoryTypeDataMapper.getByTitle("EXPENSE"));

        CategoryDataMapper categoryDataMapper = new CategoryDataMapper();
        categoryDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println(categoryDataMapper.getById(1));

        System.out.println(categoryDataMapper.getByTitle("Зарплата основная"));

        CategoryType categoryType = new CategoryType();
        categoryType.setTitle("TEST");
        categoryTypeDataMapper.insert(categoryType);
        categoryTypeDataMapper.getAll().stream().forEach(System.out::println);

        categoryType = categoryTypeDataMapper.getById(1);
        categoryType.setTitle("TEST");
        categoryTypeDataMapper.update(categoryType);
        categoryTypeDataMapper.getAll().stream().forEach(System.out::println);

        categoryType = categoryTypeDataMapper.getById(3);
        categoryTypeDataMapper.delete(categoryType);
        categoryTypeDataMapper.getAll().stream().forEach(System.out::println);
    }
}
