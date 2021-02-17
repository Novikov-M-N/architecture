package com.github.novikovmn.architect.utils.datamapper;

import com.github.novikovmn.architect.domain.Category;
import com.github.novikovmn.architect.domain.FinancialEntry;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FinancialEntryDataMapper extends DataMapper<FinancialEntry, Long> {
    private final String TABLE = super.getTABLE();
    private final String SQL_SELECT = super.getSQL_SELECT();
    private final String SQL_INSERT = "INSERT INTO " + TABLE + " (date, money, category, note) values (?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE " + TABLE + " SET date = ?, money = ?, category = ?, note = ? WHERE id = ?";

    private static FinancialEntryDataMapper instance;

    public static FinancialEntryDataMapper getInstance() {
        if (instance == null) instance = new FinancialEntryDataMapper();
        return instance;
    }

    private FinancialEntryDataMapper() {
        super("financial_entries");
    }

    private final MoneyDataMapper moneyDataMapper = MoneyDataMapper.getInstance();
    private final CategoryDataMapper categoryDataMapper = CategoryDataMapper.getInstance();

    @Override
    protected FinancialEntry create(ResultSet resultSet) throws SQLException {
        FinancialEntry financialEntry = new FinancialEntry();
        financialEntry.setId(resultSet.getLong("id"));
        financialEntry.setDate(resultSet.getDate("date"));
        financialEntry.setMoney(moneyDataMapper.getById(resultSet.getLong("money")));
        financialEntry.setCategory(categoryDataMapper.getById(resultSet.getInt("category")));
        financialEntry.setNote(resultSet.getString("note"));
        return financialEntry;
    }

    @Override
    public FinancialEntry getById(Long id) {
        // Логирование для тестирования identity map - отслеживать, когда производилось обращение к БД,
        // а когда объект получался из кэша.
        System.out.println("Обращение к базе данных по id...");
        return super.getById(id);
    }

    @Override
    public void insert(FinancialEntry domain) {
        try (var statement = super.getPreparedStatement(SQL_INSERT)) {
            statement.setDate(1, new Date(domain.getDate().getTime()));
            statement.setLong(2, domain.getMoney().getId());
            statement.setInt(3,domain.getCategory().getId());
            statement.setString(4, domain.getNote());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(FinancialEntry domain) {
        try (var statement = super.getPreparedStatement(SQL_UPDATE)) {
            statement.setDate(1, (Date) domain.getDate());
            statement.setLong(2, domain.getMoney().getId());
            statement.setInt(3,domain.getCategory().getId());
            statement.setString(4, domain.getNote());
            statement.setLong(5, domain.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FinancialEntry> getByCategory(Category category) {
        List<FinancialEntry> result = new ArrayList<>();
        try (var statement = super.getPreparedStatement(SQL_SELECT + "WHERE category = ?")) {
            statement.setInt(1, category.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next());
            result.add(create(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
