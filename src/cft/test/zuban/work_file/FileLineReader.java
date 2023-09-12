package cft.test.zuban.work_file;

import cft.test.zuban.comparer.Compare;

import java.io.*;

public class FileLineReader {
    private final String filePath;
    private final boolean isMergeSortDecrease;
    private int start;
    private long end;
    private long position;
    private boolean isSortedAscending = true;
    private boolean isSortedDescending = true;

    public FileLineReader(String filePath, Compare compare, boolean isMergeSortDecrease) {
        this.filePath = filePath;
        this.isMergeSortDecrease = isMergeSortDecrease;

        try {
            checkFileSortDirection(filePath, compare);

            try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
                long fileLength = file.length();
                long pointer = 0;

                start = 0;
                end = fileLength;

                StringBuilder reversedLine = new StringBuilder();

                while (pointer < end) {
                    file.seek(pointer);
                    char currentChar = (char) file.readByte();
                    pointer++;

                    if (currentChar == '\r') {
                        if (reversedLine.indexOf(" ") != -1 || reversedLine.isEmpty()) {
                            end = pointer - 1;
                            break;
                        }
                        reversedLine.setLength(0);
                    } else {
                        if (currentChar != '\n') {
                            reversedLine.append(currentChar);
                        }
                    }
                }
            }

            if (isMergeSortDecrease) {
                if (isSortedAscending) {
                    position = end;
                } else {
                    position = start;
                }
            } else {
                if (isSortedDescending) {
                    position = end;
                } else {
                    position = start;
                }
            }
        } catch (IOException e) {
            System.out.println("Файл " + filePath + " не отсортирован");
            System.exit(0);
        }
    }


    public String readLine() {
        if (isMergeSortDecrease) {
            if (isSortedAscending) {
                return readFileBottomToTop();
            } else {
                return readFileTopToBottom();
            }
        } else {
            if (isSortedDescending) {
                return readFileBottomToTop();
            } else {
                return readFileTopToBottom();
            }
        }
    }

    private String readFileTopToBottom() {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            long pointer = position;

            StringBuilder reversedLine = new StringBuilder();

            while (pointer < end) {
                file.seek(pointer);
                char currentChar = (char) file.readByte();
                pointer++;

                if (currentChar == '\r') {
                    if (reversedLine.indexOf(" ") == -1 && !reversedLine.isEmpty()) {
                        position = pointer;
                        return reversedLine.toString();
                    }
                } else {
                    if (currentChar != '\n') {
                        reversedLine.append(currentChar);
                    }
                }

                if (pointer == end) {
                    position = pointer;
                    return reversedLine.toString();
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
            pointer--;

            StringBuilder reversedLine = new StringBuilder();

            while (pointer >= 0) {
                file.seek(pointer);
                char currentChar = (char) file.readByte();

                pointer--;

                if (currentChar == '\n' || currentChar == '\r' || pointer == -1) {
                    if (pointer == -1) {
                        reversedLine.append(currentChar);
                    }

                    if (!reversedLine.isEmpty() && reversedLine.indexOf(" ") == -1) {
                        this.position = pointer;
                        return reversedLine.reverse().toString();
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

    private void checkFileSortDirection(String filePath, Compare compare) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {

            String currentLine = reader.readLine();
            String previousLine = reader.readLine();

            isSortedAscending = false;
            isSortedDescending = false;

            if (currentLine != null && previousLine != null) {
                if (currentLine.isEmpty() || currentLine.contains(" ")) {
                    isSortedAscending = true;
                    return;
                }

                if (previousLine.isEmpty() || previousLine.contains(" ")) {
                    isSortedAscending = true;
                    return;
                }

                if (isMergeSortDecrease) {
                    if (compare.isNextValid(currentLine, previousLine)) {
                        isSortedAscending = true;
                    } else {
                        isSortedDescending = true;
                    }
                } else {
                    if (compare.isNextValid(previousLine, currentLine)) {
                        isSortedAscending = true;
                    } else {
                        isSortedDescending = true;
                    }
                }
            }
        }
    }
}
