package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Словарь - имитация базы данных. Поддерживает операции добавления записей и поиска по значениям полей.
 * Возвращает данные в текстовом формате.
 * Операции редактирования и удаления записей не реализованы за ненадобностью в данной реализации.
 * Синглтон
 */
public class Dictionary {
    private Integer idCounter = 0;
    private final List<String> foreignList = new ArrayList<>();
    private final List<String> translationList = new ArrayList<>();

    private static Dictionary instance = null;

    private Dictionary() {}

    public static Dictionary getInstance() {
        if (instance == null) { instance = new Dictionary(); }
        return instance;
    }

    public String getById(Integer id) throws SQLException {
        String foreign = foreignList.get(id);
        String translation = translationList.get(id);
        if (foreign == null || translation == null) {
            throw new SQLException("Not found word with id = " + id);
        } else {
            return id + " " + foreign + " " + translation;
        }
    }

    public String getByForeign(String foreign) throws SQLException {
        int id = foreignList.indexOf(foreign);
        if (id == -1) {
            throw new SQLException("Not found word with foreign = " + foreign);
        } else {
            return getById(id);
        }
    }

    public String getByTranslation(String translation) throws SQLException {
        int id = translationList.indexOf(translation);
        if (id == -1) {
            throw new SQLException("Not found word with translation = " + translation);
        } else {
            return getById(id);
        }
    }

    public List<String> getAll() {
        List<String> result = new ArrayList<>();
        for (String foreign : foreignList) {
            int id = foreignList.indexOf(foreign);
            String translation = translationList.get(id);
            result.add(id + " " + foreign + " " + translation);
        }
        return result;
    }

    public void add(String foreign, String translation) throws SQLException {
        if (foreignList.contains(foreign)) {
            throw new SQLException("Word with foreign = " + foreign + " already exist");
        }
        if (translationList.contains(translation)) {
            throw new SQLException("Word with translation = " + translation + " already exist");
        }
        foreignList.add(idCounter, foreign);
        translationList.add(idCounter, translation);
        this.idCounter++;
    }

}
