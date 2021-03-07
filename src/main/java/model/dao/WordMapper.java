package model.dao;

import model.domain.Word;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Объект, отвечающий за обмен данными с БД.
 * Синглтон
 * Вывод на печать исключений закомментирован, т.к. у нас вьюха реализована в консоли, и стек трейсы ей мешают
 */
public class WordMapper {
    // Как правило, связь дата маппера и БД осуществляется жёстко при создании дата маппера.
    private final Dictionary dictionary = Dictionary.getInstance();

    private static WordMapper instance = null;

    private WordMapper() {}

    public static WordMapper getInstance() {
        if (instance == null) { instance = new WordMapper(); }
        return instance;
    }

    /**
     * Преобразует данные из формата БД в сущности предметной области программы. В данном случае - в слова
     * @param inputString строка данных из БД
     * @return готовый объект - слово
     */
    private Word parse(String inputString) {
        String[] fields = inputString.split(" ");
        Integer id = Integer.valueOf(fields[0]);
        String foreign = fields[1];
        String translation = fields[2];
        return new Word(id, foreign, translation);
    }

    public List<Word> getAll() {
        List<Word> result = new ArrayList<>();
        List<String> inputStrings = dictionary.getAll();
        for (String inputString : inputStrings) {
            result.add(parse(inputString));
        }
        return result;
    }

    public Word getByForeign(String foreign) {
        Word result = null;
        try {
            result = parse(dictionary.getByForeign(foreign));
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return result;
    }

    public Word getByTranslation(String translation) {
        Word result = null;
        try {
            result = parse(dictionary.getByTranslation(translation));
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return result;
    }

    public Word add(Word word) {
        try {
            dictionary.add(word.getForeign(), word.getTranslation());
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        try {
            return parse(dictionary.getByForeign(word.getForeign()));
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return null;
    }
}
