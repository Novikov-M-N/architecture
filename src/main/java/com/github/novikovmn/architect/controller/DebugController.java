package com.github.novikovmn.architect.controller;

import com.github.novikovmn.architect.domain.*;
import com.github.novikovmn.architect.repository.CategoryRepository;
import com.github.novikovmn.architect.repository.CategoryTypeRepository;
import com.github.novikovmn.architect.repository.CurrencyRepository;
import com.github.novikovmn.architect.repository.MoneyRepository;
import com.github.novikovmn.architect.service.FinancialEntryService;
import com.github.novikovmn.architect.utils.FinancialEntryBuilder;
import com.github.novikovmn.architect.utils.FinancialEntryDataClass;
import com.github.novikovmn.architect.utils.LoggedFinancialEntryBuilder;
import com.github.novikovmn.architect.utils.SimpleFinancialEntryBuilder;
import com.github.novikovmn.architect.utils.commands.Command;
import com.github.novikovmn.architect.utils.commands.CreateFinancialEntryCommand;
import com.github.novikovmn.architect.utils.commands.DeleteFinancialEntryCommand;
import com.github.novikovmn.architect.utils.datamapper.*;
import com.github.novikovmn.architect.utils.datamapper.identitymap.FinancialEntryIdentityMap;
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

        // Создаём дата маппер для сущностей "Тип категории"
        CategoryTypeDataMapper categoryTypeDataMapper = CategoryTypeDataMapper.getInstance();
        System.out.println();
        System.out.println("Проверка дата маппера для сущностей CategoryType:");

        System.out.println("Запрос всех сущностей из базы:");
        categoryTypeDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Запрос по id = 1:");
        System.out.println(categoryTypeDataMapper.getById(1));

        System.out.println("Запрос по наименованию 'EXPENSE':");
        System.out.println(categoryTypeDataMapper.getByTitle("EXPENSE"));

        System.out.println("Создание новой сущности:");
        CategoryType categoryType = new CategoryType();
        categoryType.setTitle("CR_TEST");
        categoryTypeDataMapper.insert(categoryType);
        categoryTypeDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Изменение сущности с id = 1:");
        categoryType = categoryTypeDataMapper.getById(1);
        categoryType.setTitle("UPD_TEST");
        categoryTypeDataMapper.update(categoryType);
        categoryTypeDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Удаление сущности с id = 3:");
        categoryType = categoryTypeDataMapper.getById(3);
        categoryTypeDataMapper.delete(categoryType);
        categoryTypeDataMapper.getAll().stream().forEach(System.out::println);


        // Создаём дата маппер для сущностей "Категория"
        CategoryDataMapper categoryDataMapper = CategoryDataMapper.getInstance();
        System.out.println();
        System.out.println("Проверка дата маппера для сущностей Category:");

        System.out.println("Запрос всех сущностей из базы:");
        categoryDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Запрос по id = 1:");
        System.out.println(categoryDataMapper.getById(1));

        System.out.println("Запрос по наименованию 'Зарплата основная':");
        System.out.println(categoryDataMapper.getByTitle("Зарплата основная"));

        System.out.println("Создание новой сущности:");
        Category category = new Category();
        category.setTitle("CREATE_TEST");
        category.setType(categoryTypeDataMapper.getByTitle("EXPENSE"));
        categoryDataMapper.insert(category);
        categoryDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Изменение сущности с id = 1:");
        category = categoryDataMapper.getById(1);
        category.setTitle("UPDATE_TEST");
        categoryDataMapper.update(category);
        categoryDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Удаление сущности с id = 7:");
        category = categoryDataMapper.getById(7);
        categoryDataMapper.delete(category);
        categoryDataMapper.getAll().stream().forEach(System.out::println);

        // Создаём дата маппер для сущностей "Валюта"
        CurrencyDataMapper currencyDataMapper = CurrencyDataMapper.getInstance();
        System.out.println();
        System.out.println("Проверка дата маппера для сущностей Currency:");

        System.out.println("Запрос всех сущностей из базы:");
        currencyDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Запрос по id = 1:");
        System.out.println(currencyDataMapper.getById(1));

        System.out.println("Запрос по условному наименованию 'USD':");
        System.out.println(currencyDataMapper.getByCharCode("USD"));

        System.out.println("Создание новой сущности:");
        Currency currency = new Currency();
        currency.setCharCode("CRT");
        currency.setSymbol("T");
        currency.setRate(BigDecimal.valueOf(12.58));
        currencyDataMapper.insert(currency);
        currencyDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Изменение сущности с id = 1:");
        currency = currencyDataMapper.getById(1);
        currency.setCharCode("UPT");
        currencyDataMapper.update(currency);
        currencyDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Удаление сущности с id = 4:");
        currency = currencyDataMapper.getById(4);
        currencyDataMapper.delete(currency);
        currencyDataMapper.getAll().stream().forEach(System.out::println);

        // Создаём дата маппер для сущностей "Денежная сумма"
        MoneyDataMapper moneyDataMapper = MoneyDataMapper.getInstance();
        System.out.println();
        System.out.println("Проверка дата маппера для сущностей Money:");

        System.out.println("Запрос всех сущностей из базы:");
        moneyDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Запрос по id = 1:");
        System.out.println(moneyDataMapper.getById(1L));

        System.out.println("Создание новой сущности:");
        Money money = new Money();
        money.setAmount(BigDecimal.valueOf(100));
        money.setCurrency(currencyDataMapper.getByCharCode("USD"));
        moneyDataMapper.insert(money);
        moneyDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Изменение сущности с id = 1:");
        money = moneyDataMapper.getById(1L);
        money.setAmount(BigDecimal.valueOf(200));
        moneyDataMapper.update(money);
        moneyDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Удаление сущности с id = 4:");
        money = moneyDataMapper.getById(4L);
        moneyDataMapper.delete(money);
        moneyDataMapper.getAll().stream().forEach(System.out::println);

        // Создаём дата маппер для сущностей "Финансовая запись"
        FinancialEntryDataMapper financialEntryDataMapper = FinancialEntryDataMapper.getInstance();
        System.out.println();
        System.out.println("Проверка дата маппера для сущностей FinancialEntry:");

        System.out.println("Запрос всех сущностей из базы:");
        financialEntryDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Создание новой сущности:");
        FinancialEntryBuilder builder = new SimpleFinancialEntryBuilder();
        builder.setMoney(moneyDataMapper.getById(1L));
        builder.setCategory(categoryDataMapper.getById(1));
        builder.setNote("Тестовая финансовая запись через дата маппер");
        try {
            financialEntryDataMapper.insert(builder.getEntry());
        } catch (Exception e) {
            e.printStackTrace();
        }
        financialEntryDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Запрос по id = 1:");
        System.out.println(financialEntryDataMapper.getById(1L));


        System.out.println("Изменение сущности с id = 1:");
        FinancialEntry financialEntry = financialEntryDataMapper.getById(1L);
        financialEntry.setNote("Изменение тестовой финансовой записи через дата маппер");
        financialEntryDataMapper.update(financialEntry);
        financialEntryDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("Удаление сущности с id = 1:");
        financialEntry = financialEntryDataMapper.getById(1L);
        financialEntryDataMapper.delete(financialEntry);
        financialEntryDataMapper.getAll().stream().forEach(System.out::println);
    }

    // Проверка работы идентити мап
    @GetMapping({"map", "map/"})
    public void identityMap() {
        // Создаём идентити мап для сущностей "Финансовая запись"
        FinancialEntryIdentityMap financialEntryIdentityMap = FinancialEntryIdentityMap.getInstance();
        System.out.println();
        System.out.println("Проверка идентити мап для сущностей FinancialEntry:");

        // Создаём несколько новых сущностей типа "Финансовая запись" через соответствующий дата маппер
        FinancialEntryDataMapper financialEntryDataMapper = FinancialEntryDataMapper.getInstance();
        MoneyDataMapper moneyDataMapper = MoneyDataMapper.getInstance();
        CategoryDataMapper categoryDataMapper = CategoryDataMapper.getInstance();
        FinancialEntryBuilder builder = new SimpleFinancialEntryBuilder();
        System.out.println("Создание новых сущностей:");
        for (int i = 1; i < 4; i++) {
            builder.setMoney(moneyDataMapper.getById((long) i));
            builder.setCategory(categoryDataMapper.getById(i));
            builder.setNote("Тестовая финансовая запись № " + i + " через дата маппер");
            try {
                financialEntryDataMapper.insert(builder.getEntry());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        financialEntryDataMapper.getAll().stream().forEach(System.out::println);

        System.out.println("кэш идентити мап пустой:");
        System.out.println(financialEntryIdentityMap);

        System.out.println("Запрос по id = 2:");
        System.out.println(financialEntryIdentityMap.getById(2L));

        System.out.println("кэш идентити мап:");
        System.out.println(financialEntryIdentityMap);

        System.out.println("Повторный запрос по id = 2:");
        System.out.println(financialEntryIdentityMap.getById(2L));

        System.out.println("кэш идентити мап:");
        System.out.println(financialEntryIdentityMap);

        System.out.println("Запрос по id = 3:");
        System.out.println(financialEntryIdentityMap.getById(3L));

        System.out.println("кэш идентити мап:");
        System.out.println(financialEntryIdentityMap);
    }
}
