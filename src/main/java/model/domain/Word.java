package model.domain;

/**
 * Объект данных - слово: иностранная форма и перевод
 */
public class Word {
    private Integer id;
    private final String foreign;
    private final String translation;

    public String getForeign() { return foreign; }
    public String getTranslation() { return translation; }

    public Word(Integer id, String foreign, String translation) {
        this.id = id;
        this.foreign = foreign;
        this.translation = translation;
    }

    public Word(String foreign, String translation) {
        this.foreign = foreign;
        this.translation = translation;
    }

    public String toString() {
        return "Word{id=" + id + ", foreign=" + foreign + ", translation=" + translation + "}";
    }
}
