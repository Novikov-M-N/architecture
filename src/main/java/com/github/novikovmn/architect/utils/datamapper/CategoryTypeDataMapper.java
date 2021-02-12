package com.github.novikovmn.architect.utils.datamapper;

import com.github.novikovmn.architect.domain.CategoryType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryTypeDataMapper extends DataMapper{
    private final String SQL_SELECT = "SELECT * FROM category_types ";
    private final String SQL_INSERT = "INSERT INTO category_types (title) values (?)";
    private final String SQL_UPDATE = "UPDATE category_types SET title = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM category_types WHERE id = ?";

    private CategoryType create(ResultSet resultSet) throws SQLException {
        CategoryType categoryType = new CategoryType();
        categoryType.setId(resultSet.getInt("id"));
        categoryType.setTitle(resultSet.getString("title"));
        return categoryType;
    }

    public List<CategoryType> getAll() {
        List<CategoryType> result = new ArrayList<>();
        try (var resultSet = super.getStatement().executeQuery(SQL_SELECT)) {
            while (resultSet.next()) {
                result.add(create(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public CategoryType getById(Integer id) {
        CategoryType categoryType = null;
        try (var statement = super.getPreparedStatement(SQL_SELECT + "WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            categoryType = create(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryType;
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

    public void insert(CategoryType categoryType) {
        try (var statement = super.getPreparedStatement(SQL_INSERT)) {
            statement.setString(1, categoryType.getTitle());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(CategoryType categoryType) {
        try (var statement = super.getPreparedStatement(SQL_UPDATE)) {
            statement.setString(1, categoryType.getTitle());
            statement.setInt(2, categoryType.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(CategoryType categoryType) {
        try (var statement = super.getPreparedStatement(SQL_DELETE)) {
            statement.setInt(1, categoryType.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
