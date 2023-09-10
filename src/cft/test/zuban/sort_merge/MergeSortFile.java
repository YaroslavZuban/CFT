package cft.test.zuban.sort_merge;

import cft.test.zuban.comparer.Compare;
import cft.test.zuban.line_parser.ArgsParser;
import cft.test.zuban.work_file.FileHelper;
import cft.test.zuban.work_keys.KeyProcessing;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * В алгоритме реализации:
 * <p>
 * 1. Сначала передаем названия файлов - входного файла для чтения, результирующего файла и временного файла.
 * <p>
 * 2. Используем функцию mergeWithOrder, которая открывает входной файл, считывает строки и передает их в
 * результирующий и временный файлы. Если входной файл пуст или не содержит строк, то операция завершается.
 * <p>
 * <p>
 * 3. Далее переходим к функции mergeSingleLine:
 * <p>
 * Открываем результирующий файл для чтения.
 * Открываем временный файл для записи.
 * Считываем строку из результирующего файла и проверяем, не пуст ли он.
 * Если результирующий файл не пуст:
 * Сравниваем считанную строку и текущую строку.
 * Если условие сравнения истинно, то записываем текущую строку, затем считанную строку. Если условие ложно,
 * просто записываем текущую строку во временный файл.
 * Если ни одна строка не была записана, то записываем текущую строку во временный файл.
 * Если результирующий файл пустой, записываем текущую строку во временный файл.
 * После завершения записи данных, переименовываем и удаляем файлы:
 * <p>
 * 4. Удаляем результирующий файл.
 * Переименовываем временный файл в результирующий файл.
 * Этот алгоритм обеспечивает слияние данных из входного файла в результирующий файл, сохраняя при этом порядок
 */

public class MergeSortFile {
    private Compare compare;
    private final String[] args;
    private boolean isFirstValue = true;

    public MergeSortFile(String[] args) {
        this.args = args;
    }

    public void run() {
        ArgsParser argsParser = new ArgsParser();
        List<String> filesList = argsParser.searchFiles(args);

        List<String> keysList = argsParser.searchKeys(args);

        FileHelper fileHelper = new FileHelper();
        fileHelper.validateFilesExist(filesList);

        compare = new KeyProcessing().identifyTypeSortAndData(keysList);

        String pathOut = filesList.get(0);
        String tempFile = "tempFile.txt";

        try {
            createOrClearFile(pathOut);
        } catch (IOException e) {
            System.out.println("Ошибка: проблема с файлом " + pathOut);
            System.exit(0);
        }

        for (int i = 1; i < filesList.size(); i++) {
            try {
                mergeWithOrder(filesList.get(i), pathOut, tempFile);
            } catch (IOException e) {
                System.out.println("Ошибка: в файле" + filesList.get(i) + " не корректные данные.");
                System.exit(0);
            }
        }
    }

    /**
     * если файла нет создаем его, если есть очистить в нем все данные
     */
    private static void createOrClearFile(String path) throws IOException {
        File file = new File(path);

        if (!file.exists()) {
            file.createNewFile();
        } else {
            FileWriter writer = new FileWriter(file);
            writer.write("");
            writer.close();
        }
    }

    private void mergeWithOrder(String sourcePath, String destinationPath, String tempPath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourcePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(" ") || line.isEmpty()) {
                    break;
                }

                mergeSingleLine(line, destinationPath, tempPath);
            }
        }
    }

    private void mergeSingleLine(String line, String destinationPath, String tempPath) throws IOException {
        boolean foundTen = true;

        try (BufferedReader reader = new BufferedReader(new FileReader(destinationPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempPath))) {

            String lineTempFile = reader.readLine();

            while (lineTempFile != null) {
                if (compare.isNextValid(lineTempFile, line) && foundTen) {
                    writer.write(line);
                    writer.newLine();
                    writer.write(lineTempFile);
                    foundTen = false;
                } else {
                    writer.write(lineTempFile);
                }

                lineTempFile = reader.readLine();

                if (lineTempFile != null) {
                    writer.newLine();
                }
            }
        }

        if (foundTen) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempPath, true))) {
                if (!isFirstValue) {
                    writer.newLine();
                }

                writer.write(line);
                isFirstValue = false;
            }
        }

        Path path = Paths.get(destinationPath);

        Files.delete(path);
        Files.move(Paths.get(tempPath), path);
    }
}