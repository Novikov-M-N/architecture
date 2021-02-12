package com.github.novikovmn.architect.utils.datamapper;

import com.github.novikovmn.architect.domain.Category;
import com.github.novikovmn.architect.domain.CategoryType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDataMapper extends DataMapper{
    private final String SQL = "SELECT * FROM categories ";

    private CategoryTypeDataMapper categoryTypeDataMapper = new CategoryTypeDataMapper();

    private Category create(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("id"));
        category.setTitle(resultSet.getString("title"));
        category.setType(categoryTypeDataMapper.getById(resultSet.getInt("type")));
        return category;
    }

    public List<Category> getAll() {
        List<Category> result = new ArrayList<>();
        try(var resultSet = super.getStatement().executeQuery(SQL)) {
            while (resultSet.next()) {
                result.add(create(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Category getById(Integer id) {
        Category category = null;
        try (var statement = super.getPreparedStatement(SQL + "WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            category = create(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    public Category getByTitle(String title) {
        Category category = null;
        try (var statement = super.getPreparedStatement(SQL + "WHERE title = ?")) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            category = create(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

}
