package com.github.novikovmn.architect.utils.datamapper;

import com.github.novikovmn.architect.domain.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDataMapper extends DataMapper<Category, Integer>{
    private final String TABLE = super.getTABLE();
    private final String SQL_SELECT = super.getSQL_SELECT();
    private final String SQL_INSERT = "INSERT INTO " + TABLE + " (title, type) values (?, ?)";
    private final String SQL_UPDATE = "UPDATE " + TABLE + " SET title = ?, type = ? WHERE id = ?";

    private static CategoryDataMapper instance;

    public static CategoryDataMapper getInstance() {
        if (instance == null) instance = new CategoryDataMapper();
        return instance;
    }

    private CategoryDataMapper() {
        super("categories");
    }

    private final CategoryTypeDataMapper categoryTypeDataMapper = CategoryTypeDataMapper.getInstance();

    protected Category create(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("id"));
        category.setTitle(resultSet.getString("title"));
        category.setType(categoryTypeDataMapper.getById(resultSet.getInt("type")));
        return category;
    }

    @Override
    public void insert(Category domain) {
        try (var statement = super.getPreparedStatement(SQL_INSERT)) {
            statement.setString(1, domain.getTitle());
            statement.setInt(2, domain.getType().getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Category domain) {
        try (var statement = super.getPreparedStatement(SQL_UPDATE)) {
            statement.setString(1, domain.getTitle());
            statement.setInt(2, domain.getType().getId());
            statement.setInt(3, domain.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Category getByTitle(String title) {
        Category category = null;
        try (var statement = super.getPreparedStatement(SQL_SELECT + "WHERE title = ?")) {
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
