package com.github.novikovmn.architect.utils.datamapper;

import java.sql.*;

public abstract class DataMapper {
    private static final String URL = "jdbc:h2:mem:mydatabase";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    protected Statement getStatement() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD).createStatement();
    }

    protected PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD).prepareStatement(sql);
    }
}
