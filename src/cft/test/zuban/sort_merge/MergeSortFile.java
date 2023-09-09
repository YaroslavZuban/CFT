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

public class MergeSortFile {
    private Compare compare;
    private final String[] args;
    private boolean isFirstValue = true;

    public MergeSortFile(String[] args) {
        this.args = args;
    }

    public void run() throws IOException {
        ArgsParser argsParser = new ArgsParser();
        List<String> filesList = argsParser.searchFiles(args);

        List<String> keysList = argsParser.searchKeys(args);

        FileHelper fileHelper = new FileHelper();
        fileHelper.validateFilesExist(filesList);

        compare = new KeyProcessing().identifyTypeSortAndData(keysList);

        String pathOut = filesList.get(0);
        String tempFile = "tempFile.txt";

        for (int i = 1; i < filesList.size(); i++) {
            if (i == 1) {
                mergeFiles(filesList.get(i), pathOut, tempFile);
            } else {
                mergeWithOrder(filesList.get(i), pathOut, tempFile);
            }
        }
    }

    private void mergeFiles(String sourcePath, String destinationPath, String tempPath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourcePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destinationPath))) {

            boolean isEmptyFile = true;

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(" ") || line.isEmpty()) {
                    break;
                }

                if (!isEmptyFile) {
                    mergeSingleLine(line, destinationPath, tempPath);
                } else {
                    writer.write(line);
                    isFirstValue = false;
                    isEmptyFile = false;
                    writer.close();
                }
            }
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