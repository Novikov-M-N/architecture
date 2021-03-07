package model.service;

/**
 * Возможные ошибки, которые контроллер может послать фронтенду в ответ на запрос.
 */
public enum Error {
    WORD_NOT_FOUND_ERROR("E001"),
    FOREIGN_ALREADY_EXIST_ERROR("E002"),
    TRANSLATION_ALREADY_EXIST_ERROR("E003"),
    UNKNOWN_ERROR("E666");

    private final String code;

    Error(String code) {
        this.code = code;
    }

    public String getCode() { return code; }
}
