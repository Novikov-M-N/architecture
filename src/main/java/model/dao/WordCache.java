package model.dao;

import model.domain.Word;

import java.util.HashMap;
import java.util.Map;

/**
 * Кэш объектов типа Word. Служит для уменьшения количества запросов к БД.
 * Содержит два отдельных кэша - по одному для каждого поля объекта, по которому требуется производить поиск.
 * Синглтон
 */
public class WordCache {
    private final Map <String, Word> foreignKeyMap = new HashMap<>();
    private final Map <String, Word> translationKeyMap = new HashMap<>();

    private static WordCache instance = null;

    private WordCache() {}

    public static WordCache getInstance() {
        if (instance == null) { instance = new WordCache(); }
        return instance;
    }

    public Word getByForeign(String foreign) { return foreignKeyMap.get(foreign); }

    public Word getByTranslaton(String translation) { return translationKeyMap.get(translation); }

    public void put(Word word) {
        foreignKeyMap.put(word.getForeign(), word);
        translationKeyMap.put(word.getTranslation(), word);
    }
}
