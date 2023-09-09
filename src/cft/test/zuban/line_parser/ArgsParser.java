package cft.test.zuban.line_parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArgsParser {
    private final List<String> keysList = Arrays.asList("-i", "-s", "-a", "-d");

    public List<String> searchKeys(String[] args) {
        List<String> resultKeys = new ArrayList<>();

        for (String key : args) {
            if (keysList.contains(key)) {
                resultKeys.add(key);
            }
        }

        validateArgs(resultKeys);
        return resultKeys;
    }

    private void validateArgs(List<String> commandLineArgs) {
        if (commandLineArgs.isEmpty()) {
            System.out.println("Ошибка: не указаны аргументы командной строки.");
            System.out.println("Используйте следующий формат для запуска программы:");
            System.out.println("sort-it.exe -i|-s -a|-d output.txt * * * *");
            System.out.println("Где:");
            System.out.println("-i для сортировки чисел, -s для сортировки строк");
            System.out.println("-a для сортировки по возрастанию, -d для сортировки по убыванию");
            System.out.println("* - имя файлов с данными для сортировки их может быть от 1 до N количества");
            System.out.println("output.txt - имя файла для сохранения отсортированных данных");
            System.out.println("Пример: sort-it.exe -i -a output.txt in1.txt");
            System.exit(0);
        }

        for (String arg : commandLineArgs) {
            if (!keysList.contains(arg) && !arg.endsWith(".txt")) {
                System.out.println("Ошибка: неизвестный аргумент - " + arg);
                System.out.println("Доступные аргументы:");
                System.out.println("-i для сортировки чисел, -s для сортировки строк");
                System.out.println("-a для сортировки по возрастанию, -d для сортировки по убыванию");
                System.out.println("Нажмите любую клавишу для завершения работы...");
                System.exit(0);
            }
        }
    }

    public List<String> searchFiles(String[] args) {
        List<String> workFilesList = new ArrayList<>();

        for (String fileName : args) {
            if (fileName.contains(".txt")) {
                workFilesList.add(fileName);
            }
        }

        return workFilesList;
    }
}