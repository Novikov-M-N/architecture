package model.dao;

import model.domain.Word;

import java.util.List;

/**
 * Репозиторий для объектов типа Word. Выступает в качестве фасада для всего обмена данными с БД.
 * Управляет работой кэша и дата маппера.
 * Синглтон.
 */
public class WordRepository {
    // Кэш и дата маппер жёстко связаны с репозиторием и в процессе работы программы не меняются.
    private final WordMapper wordMapper = WordMapper.getInstance();
    private final WordCache wordCache = WordCache.getInstance();

    private static WordRepository instance = null;

    private WordRepository() {}

    public static WordRepository getInstance() {
        if (instance == null) { instance = new WordRepository(); }
        return instance;
    }

    public List<Word> getAll() {
        return wordMapper.getAll();
    }

    public Word getByForeign(String foreign) {
        Word result = wordCache.getByForeign(foreign);
        if (result == null) {
            result = wordMapper.getByForeign(foreign);
            if (result != null) { wordCache.put(result); } // если объект найден, помещаем ссылку на него в кэш
        }
        return result;
    }

    public Word getByTranslation(String translation) {
        Word result = wordCache.getByTranslaton(translation);
        if (result == null) {
            result = wordMapper.getByTranslation(translation);
            if (result != null) { wordCache.put(result); } // если объект найден, помещаем ссылку на него в кэш
        }
        return result;
    }

    public Word add(Word inputWord) {
        Word newWord = wordMapper.add(inputWord);
        if (newWord != null) { wordCache.put(newWord); } // если объект найден, помещаем ссылку на него в кэш
        return newWord;
    }
}
