package view;

import controller.WordController;
import model.service.Error;

import java.util.Scanner;

public class ConsoleView{
    private final WordController controller;

    public ConsoleView(WordController controller) {
        this.controller = controller;
    }

    public void start() {
        show("Добро пожаловать в словарь");
        Scanner in = new Scanner(System.in);
        String line;
        String command;
        boolean exit = false;
        while (!exit) {
            line = in.nextLine();
            String[] args = line.split(" ");
            command = args[0];
            switch (command) {
                case "+" -> {
                    if (args.length > 2) {
                        show(addWord(args[1], args[2]));
                    } else {
                        show("Не все данные указаны. Проверьте данные и внимательно смотрите справочник по командам");
                    }
                }
                case "q" -> {
                    show("До свидания");
                    exit = true;
                }
                default -> {
                    show(translate(command));
                }
            }
        }
    }

    public String translate(String inputString) {
        String result = controller.translate(inputString);
        if (result.equals(Error.WORD_NOT_FOUND_ERROR.getCode())) {
            return "слово в словаре не найдено";
        } else return result;
    }

    private String addWord(String foreign, String translation) {
        String result = controller.add(foreign, translation);
        if (result.equals(Error.FOREIGN_ALREADY_EXIST_ERROR.getCode())) {
            return "Слово с иностранным значением \"" + foreign + "\" уже имеется в словаре и не может быть добавлено повторно";
        }
        if (result.equals(Error.TRANSLATION_ALREADY_EXIST_ERROR.getCode())) {
            return "Слово с переводом \"" + translation + "\" уже имеется в словаре и не может быть добавлено повторно";
        }
        if (result.equals(Error.UNKNOWN_ERROR.getCode())) {
            return "Неведомая фигня случилась. Попробуйте повторить операцию, либо обратитесь к разработчикам";
        }
        return "Новое слово успешно добавлено в словарь: " + result;
    }

    private void show(String message) {
        char placeholder = '*';
        int size = message.length() + 4;
        for (int i = 0; i < size; i++) {
            System.out.print(placeholder);
        }
        System.out.println();
        System.out.println(placeholder + " " + message + " " + placeholder);
        for (int i = 0; i < size; i++) {
            System.out.print(placeholder);
        }
        System.out.println();
        System.out.println("Введите слово для получения его перевода, либо команду");
        System.out.println("Справочник по командам:");
        System.out.println("+ иностранное_слово перевод - добавить новое слово в словарь");
        System.out.println("q - выход");
    }
}
