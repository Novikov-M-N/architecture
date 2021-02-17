package com.github.novikovmn.architect.utils.datamapper;

import com.github.novikovmn.architect.domain.Money;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyDataMapper extends DataMapper<Money, Long>{
    private final String TABLE = super.getTABLE();
    private final String SQL_INSERT = "INSERT INTO " + TABLE + " (amount, currency) values (?,?)";
    private final String SQL_UPDATE = "UPDATE " + TABLE + " SET amount = ?, currency = ? WHERE id = ?";

    private static MoneyDataMapper instance;

    public static MoneyDataMapper getInstance() {
        if (instance == null) instance = new MoneyDataMapper();
        return instance;
    }

    private MoneyDataMapper() { super("moneys"); }

    private final CurrencyDataMapper currencyDataMapper = CurrencyDataMapper.getInstance();

    @Override
    protected Money create(ResultSet resultSet) throws SQLException {
        Money money = new Money();
        money.setId(resultSet.getLong("id"));
        money.setAmount(resultSet.getBigDecimal("amount"));
        money.setCurrency(currencyDataMapper.getById(resultSet.getInt("currency")));
        return money;
    }

    @Override
    public void insert(Money domain) {
        try (var statement = super.getPreparedStatement(SQL_INSERT)) {
            statement.setBigDecimal(1, domain.getAmount());
            statement.setInt(2, domain.getCurrency().getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Money domain) {
        try (var statement = super.getPreparedStatement(SQL_UPDATE)) {
            statement.setBigDecimal(1, domain.getAmount());
            statement.setInt(2, domain.getCurrency().getId());
            statement.setLong(3, domain.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
