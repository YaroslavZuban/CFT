package cft.test.zuban.sort_merge;

import cft.test.zuban.comparer.Compare;
import cft.test.zuban.line_parser.ArgsParser;
import cft.test.zuban.work_file.FileHelper;
import cft.test.zuban.work_file.FileLineReader;
import cft.test.zuban.work_keys.KeyProcessing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MergeSortFile {
    private String pathOut;
    private final String[] args;

    public MergeSortFile(String[] args) {
        this.args = args;
    }

    public void run() {
        ArgsParser argsParser = new ArgsParser();
        List<String> filesList = argsParser.searchFiles(args);

        List<String> keysList = argsParser.searchKeys(args);

        FileHelper fileHelper = new FileHelper();
        fileHelper.validateFilesExist(filesList);

        Compare compare = new KeyProcessing().identifyTypeSortAndData(keysList);

        pathOut = filesList.get(0);

        try {
            createOrClearFile(pathOut);
        } catch (IOException e) {
            System.out.println("Ошибка: проблема с файлом " + pathOut);
            System.exit(0);
        }

        try {
            boolean isSortDecrease = keysList.contains("-d");

            List<FileLineReader> readersList = new ArrayList<>();

            for (int i = 1; i < filesList.size(); i++) {
                readersList.add(new FileLineReader(filesList.get(i), compare, isSortDecrease));
            }

            List<String> nonEmptyLines = fetchNonEmptyLines(readersList);

            String currentLine = nonEmptyLines.get(0);
            int currentIndex = 0;

            while (!isNullsList(nonEmptyLines)) {
                for (int i = 0; i < nonEmptyLines.size(); i++) {
                    String line = nonEmptyLines.get(i);

                    if (currentLine == null && line != null) {
                        currentLine = line;
                        currentIndex = i;
                    }

                    if (line != null && compare.isNextValid(currentLine, line)) {
                        currentLine = line;
                        currentIndex = i;
                    }
                }

                FileLineReader reader = readersList.get(currentIndex);

                if (reader != null) {
                    String line = reader.readLine();

                    if (line != null && !line.contains(" ") && !line.isEmpty()) {
                        nonEmptyLines.set(currentIndex, line);
                    } else {
                        nonEmptyLines.set(currentIndex, null);
                        readersList.set(currentIndex, null);
                    }

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathOut, true))) {
                        writer.write(currentLine);

                        if (line != null && line.isEmpty()) {
                            currentLine = null;
                        } else {
                            currentLine = line;
                        }

                        if (!isNullsList(nonEmptyLines)) {
                            writer.newLine();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка. Проверьте данные!");
            System.exit(0);
        }
    }

    private static List<String> fetchNonEmptyLines(List<FileLineReader> readersList) {
        List<String> lineReaderList = new ArrayList<>();

        for (FileLineReader fileLineReader : readersList) {
            String line = fileLineReader.readLine();

            if (line != null && !line.isEmpty() && !line.contains(" ")) {
                lineReaderList.add(line);
            } else {
                lineReaderList.add(null);
            }
        }

        return lineReaderList;
    }

    /*Проверка что в списке одни нули*/
    private static boolean isNullsList(List<String> list) {
        for (String line : list) {
            if (line != null) {
                return false;
            }
        }

        return true;
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
}