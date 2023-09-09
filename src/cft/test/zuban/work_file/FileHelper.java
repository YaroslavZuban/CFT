package cft.test.zuban.work_file;

import java.io.File;
import java.util.List;

public class FileHelper {
    public void validateFilesExist(List<String> filePaths) {
        for (int i = 1; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(0));

            if (!file.exists()) {
                System.out.println("Файл " + filePaths.get(0) + " не найден");
                filePaths.remove(i);
                i--;
            }
        }

        if (filePaths.isEmpty()) {
            System.out.println("Не указаны входной и выходной файлы. Пожалуйста, укажите их и перезапустите программу.");
            System.out.println("Для завершения работы нажмите любую клавишу...");
            System.exit(0);
        }

        if (filePaths.size() == 1) {
            System.out.println("Укажите оба входных файла и перезапустите программу.");
            System.out.println("Для завершения работы нажмите любую клавишу...");
            System.exit(0);
        }
    }
}