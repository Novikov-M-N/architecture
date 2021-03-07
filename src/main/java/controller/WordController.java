package controller;

import model.service.Error;
import model.domain.Word;
import model.dao.WordRepository;

/**
 * Контроллер. Отвечает за взаимодействие между пользовательским интерфейсом и бизнес-логикой.
 * Синглтон.
 */
public class WordController {

    private final WordRepository repository = WordRepository.getInstance();

    private static WordController instance = null;

    private WordController() {}

    public static WordController getInstance() {
        if (instance == null) {
            instance = new WordController();
        }
        return instance;
    }

    /**
     * Выдача перевода введённого пользователем слова
     * @param inputString Слово, введённое пользователем
     * @return Перевод слова, введённого пользователем, либо код ошибки, если слово не найдено
     */
    public String translate(String inputString) {
        Word word = repository.getByForeign(inputString);
        if (word != null) { return word.getTranslation(); }
        word = repository.getByTranslation(inputString);
        if (word != null) { return word.getForeign(); }
        return Error.WORD_NOT_FOUND_ERROR.getCode();
    }

    /**
     * Вносит в слварь новое слово с иностранным значением и переводом, полученными от фронтенда.
     * Проверяет, чтобы в словарь не вносились повторно слова, которые там уже есть
     * @param foreign Иностранное значение слова, полученное от фронтенда
     * @param translation Перевод слова, полученный от фронтенда
     * @return Успешно внесённое слово в оговорённом формате, понятном фронтенду
     * (в данном случае иностранное значение и перевод, разделённные пробелом), либо код ошибки, если таковая будет
     */
    public String add(String foreign, String translation) {
        Word word = repository.getByForeign(foreign);
        if (word != null) { return Error.FOREIGN_ALREADY_EXIST_ERROR.getCode(); }
        word = repository.getByTranslation(translation);
        if (word != null) { return Error.TRANSLATION_ALREADY_EXIST_ERROR.getCode(); }
        word = repository.add(new Word(foreign, translation));
        if (word != null) { return word.getForeign() + " " + word.getTranslation(); }
        else { return Error.UNKNOWN_ERROR.getCode(); }
    }
}
