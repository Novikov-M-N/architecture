package com.github.novikovmn.architect.utils.datamapper;

import com.github.novikovmn.architect.domain.CategoryType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryTypeDataMapper extends DataMapper<CategoryType, Integer>{
    private final String TABLE = super.getTABLE();
    private final String SQL_SELECT = super.getSQL_SELECT();
    private final String SQL_INSERT = "INSERT INTO " + TABLE + " (title) values (?)";
    private final String SQL_UPDATE = "UPDATE " + TABLE + " SET title = ? WHERE id = ?";

    private static CategoryTypeDataMapper instance;

    public static CategoryTypeDataMapper getInstance() {
        if (instance == null) instance = new CategoryTypeDataMapper();
        return instance;
    }

    private CategoryTypeDataMapper() {
        super("category_types");
    }

    protected CategoryType create(ResultSet resultSet) throws SQLException {
        CategoryType categoryType = new CategoryType();
        categoryType.setId(resultSet.getInt("id"));
        categoryType.setTitle(resultSet.getString("title"));
        return categoryType;
    }

    @Override
    public void insert(CategoryType domain) {
        try (var statement = super.getPreparedStatement(SQL_INSERT)) {
            statement.setString(1, domain.getTitle());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CategoryType domain) {
        try (var statement = super.getPreparedStatement(SQL_UPDATE)) {
            statement.setString(1, domain.getTitle());
            statement.setInt(2, domain.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CategoryType getByTitle(String title) {
        CategoryType categoryType = null;
        try (var statement = super.getPreparedStatement(SQL_SELECT + "WHERE title = ?")) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            categoryType = create(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryType;
    }
}
