package cft.test.zuban.work_file;

import java.io.*;

public class FileReader {
    private final String filePath;
    private int start;
    private long end;
    private long position;

    private final boolean isMergeSortDecrease;
    private boolean isSortedAscending;

    private boolean isSortedDescending;

    public FileReader(String filePath, boolean isMergeSortDecrease) {
        this.filePath = filePath;
        this.isMergeSortDecrease = isMergeSortDecrease;

        try {
            checkFileSortDirection(filePath);

            try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
                long fileLength = file.length();
                long pointer = fileLength - 1;

                StringBuilder reversedLine = new StringBuilder();

                while (pointer >= 0) {
                    file.seek(pointer);
                    char currentChar = (char) file.readByte();
                    pointer--;

                    if (currentChar == '\n') {
                        if (reversedLine.indexOf(" ") != -1 || reversedLine.isEmpty()) {
                            end = pointer;
                            break;
                        }

                        System.out.println(reversedLine.reverse());
                        reversedLine.setLength(0);
                    } else {
                        reversedLine.append(currentChar);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Файл " + filePath + " не отсортирован");
            System.exit(0);
        }

        if (isMergeSortDecrease && isSortedAscending || !isMergeSortDecrease && isSortedDescending){
            position=end;
        }else {
            position=start;
        }

    }

    public String readLine() {
        if (isMergeSortDecrease && isSortedAscending || !isMergeSortDecrease && isSortedDescending){
            return readFileBottomToTop();
        }

        return readFileTopToBottom();
    }

    private String readFileTopToBottom() {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            long pointer = position;

            StringBuilder reversedLine = new StringBuilder();

            while (pointer < end) {
                file.seek(pointer);
                char currentChar = (char) file.readByte();
                pointer++;

                if (currentChar == '\n') {
                    if (reversedLine.indexOf(" ") != -1 || reversedLine.isEmpty()) {
                        this.position = pointer;
                        return  reversedLine.toString();
                    }
                } else {
                    reversedLine.append(currentChar);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    private String readFileBottomToTop() {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            long pointer = position;

            StringBuilder reversedLine = new StringBuilder();

            while (pointer >= 0) {
                file.seek(pointer);
                char currentChar = (char) file.readByte();
                pointer--;

                if (currentChar == '\n') {
                    if (reversedLine.indexOf(" ") != -1 || reversedLine.isEmpty()) {
                        this.position = pointer;
                        return  reversedLine.reverse().toString();
                    }
                } else {
                    reversedLine.append(currentChar);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private void checkFileSortDirection(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {

            String currentLine = reader.readLine();
            String previousLine = currentLine;

            isSortedAscending = true;
            isSortedDescending = true;

            while (currentLine != null) {
                if (currentLine.compareTo(previousLine) < 0) {
                    isSortedAscending = false;
                } else if (currentLine.compareTo(previousLine) > 0) {
                    isSortedDescending = false;
                }

                if (!isSortedAscending && !isSortedDescending) {
                    throw new IOException();
                }

                previousLine = currentLine;
                currentLine = reader.readLine();
            }
        }
    }
}
