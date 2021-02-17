package com.github.novikovmn.architect.utils.datamapper;

import com.github.novikovmn.architect.domain.Domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DataMapper<DomainType extends Domain, IdType> {
    private static final String URL = "jdbc:h2:mem:mydatabase";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    private final String TABLE;
    private final String SQL_SELECT;
    private final String SQL_DELETE;

    protected DataMapper(String TABLE) {
        this.TABLE = TABLE;
        this.SQL_SELECT = "SELECT * FROM " + TABLE + " ";
        this.SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id = ?";
    }

    public String getTABLE() { return TABLE; }
    public String getSQL_SELECT() { return SQL_SELECT; }

    protected Statement getStatement() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD).createStatement();
    }

    protected PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD).prepareStatement(sql);
    }

    protected abstract DomainType create(ResultSet resultSet) throws SQLException;

    public List<DomainType> getAll() {
        List<DomainType> result = new ArrayList<>();
        try (var resultSet = getStatement().executeQuery(SQL_SELECT)) {
            while (resultSet.next()) {
                result.add(create(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public DomainType getById(IdType id) {
        DomainType result = null;
        try (var statement = getPreparedStatement(SQL_SELECT + "WHERE id = ?")) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result = create(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public abstract void insert(DomainType domain);
    public abstract void update(DomainType domain);
    public void delete(DomainType domain) {
        try (var statement = getPreparedStatement(SQL_DELETE)) {
            statement.setObject(1, domain.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
