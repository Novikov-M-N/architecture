package com.github.novikovmn.architect.utils.datamapper;

import com.github.novikovmn.architect.domain.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyDataMapper extends DataMapper<Currency, Integer> {
    private final String TABLE = super.getTABLE();
    private final String SQL_SELECT = super.getSQL_SELECT();
    private final String SQL_INSERT = "INSERT INTO " + TABLE + " (char_code, symbol, rate) values (?,?,?)";
    private final String SQL_UPDATE = "UPDATE " + TABLE + " SET char_code = ?, symbol = ?, rate = ? WHERE id = ?";

    private static CurrencyDataMapper instance;

    public static CurrencyDataMapper getInstance() {
        if (instance == null) instance = new CurrencyDataMapper();
        return instance;
    }

    private CurrencyDataMapper() {
        super("currencies");
    }

    @Override
    protected Currency create(ResultSet resultSet) throws SQLException {
        Currency currency = new Currency();
        currency.setId(resultSet.getInt("id"));
        currency.setCharCode(resultSet.getString("char_code"));
        currency.setSymbol(resultSet.getString("symbol"));
        currency.setRate(resultSet.getBigDecimal("rate"));
        return currency;
    }

    @Override
    public void insert(Currency domain) {
        try (var statement = super.getPreparedStatement(SQL_INSERT)) {
            statement.setString(1, domain.getCharCode());
            statement.setString(2, domain.getSymbol());
            statement.setBigDecimal(3, domain.getRate());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Currency domain) {
        try (var statement = super.getPreparedStatement(SQL_UPDATE)) {
            statement.setString(1, domain.getCharCode());
            statement.setString(2, domain.getSymbol());
            statement.setBigDecimal(3, domain.getRate());
            statement.setInt(4, domain.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Currency getByCharCode(String charCode) {
        Currency currency = new Currency();
        try (var statement = super.getPreparedStatement(SQL_SELECT + "WHERE char_code = ?")) {
            statement.setString(1, charCode);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            currency = create(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currency;
    }
}
