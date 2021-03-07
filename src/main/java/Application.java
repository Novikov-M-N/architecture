import controller.WordController;
import model.domain.Word;
import model.dao.WordRepository;
import view.ConsoleView;

/**
 * Электронный словарь. Возможности: получение перевода иностранного слова,
 * получение иностранного слова по переводу (направление перевода программа определяет самостоятельно),
 * добавление новых слов в словарь
 */
public class Application {
    public static void main(String[] args) {
        // Первоначальная сборка и настройка компонентов системы

        // Создаём репозиторий. Он автоматически создаёт остальные объекты, необходимые для работы с БД:
        // дата маппер и кэш. Дата маппер инициализирует подключение к БД (в данном случае - создаёт имитацию БД)
        WordRepository repository = WordRepository.getInstance();

        // Добавляем несколько первоначальных записей в БД
        repository.add(new Word("world", "мир"));
        repository.add(new Word("work", "труд"));
        repository.add(new Word("may", "май"));

        // Создаём фронтенд, назначаем ему контроллер, к которому он будет обращаться, и стартуем.
        ConsoleView view = new ConsoleView(WordController.getInstance());
        view.start();
    }
}
