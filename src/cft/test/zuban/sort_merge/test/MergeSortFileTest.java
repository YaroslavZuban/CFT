package cft.test.zuban.sort_merge.test;

import cft.test.zuban.sort_merge.MergeSortFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;


class MergeSortFileTest {
    @Test
    void run() throws IOException {
        //Папка test_file_1, в данном файле находятся целочисленный тип

        String path_test_1 = "C:/Users/ben70/IdeaProjects/CFT/src/cft/test/zuban/sort_merge/test/test_file_1/";

        String test_file_1_in1 = path_test_1 + "test_file_1_in1.txt";
        String test_file_1_in2 = path_test_1 + "test_file_1_in2.txt";
        String test_file_1_in3 = path_test_1 + "test_file_1_in3.txt";

        String test_file_1_out_ascending = path_test_1 + "test_file_1_out_ascending.txt";
        String test_file_1_out_descending = path_test_1 + "test_file_1_out_descending.txt";

        File file_1_out_ascending = new File(test_file_1_out_ascending);
        File file_1_out_descending = new File(test_file_1_out_descending);

        File file_1_result_ascending = new File(path_test_1 + "test_file_1_result_ascending.txt");
        File file_1_result_descending = new File(path_test_1 + "test_file_1_result_descending.txt");

        String[] array_test_1_ascending = {"-i", "-a", test_file_1_out_ascending,
                test_file_1_in1,
                test_file_1_in2,
                test_file_1_in3};

        MergeSortFile test1 = new MergeSortFile(array_test_1_ascending);
        test1.run();

        Assertions.assertTrue(isEqual(file_1_out_ascending.toPath(), file_1_result_ascending.toPath()));

        String[] array_test_1_descending = {"-i", "-d",
                test_file_1_out_descending,
                test_file_1_in1,
                test_file_1_in2,
                test_file_1_in3};

        MergeSortFile test2 = new MergeSortFile(array_test_1_descending);
        test2.run();

        Assertions.assertTrue(isEqual(file_1_out_descending.toPath(), file_1_result_descending.toPath()));

        //Папка test_file_2, в данном файле находятся целочисленный тип
        // в test_file_2_in3.txt идет пробел на 3 строке

        String path_test_2 = "C:/Users/ben70/IdeaProjects/CFT/src/cft/test/zuban/sort_merge/test/test_file_2/";

        String test_file_2_in1 = path_test_2 + "test_file_2_in1.txt";
        String test_file_2_in2 = path_test_2 + "test_file_2_in2.txt";
        String test_file_2_in3 = path_test_2 + "test_file_2_in3.txt";

        String test_file_2_out_ascending = path_test_2 + "test_file_2_out_ascending.txt";
        String test_file_2_out_descending = path_test_2 + "test_file_2_out_descending.txt";

        File file_2_out_ascending = new File(test_file_2_out_ascending);
        File file_2_out_descending = new File(test_file_2_out_descending);

        File file_2_result_ascending = new File(path_test_2 + "test_file_2_result_ascending.txt");
        File file_2_result_descending = new File(path_test_2 + "test_file_2_result_descending.txt");

        String[] array_test_2_ascending = {"-i", "-a",
                test_file_2_out_ascending,
                test_file_2_in1,
                test_file_2_in2,
                test_file_2_in3};

        MergeSortFile test3 = new MergeSortFile(array_test_2_ascending);
        test3.run();

        Assertions.assertTrue(isEqual(file_2_out_ascending.toPath(), file_2_result_ascending.toPath()));

        String[] array_test_2_descending = {"-i", "-d",
                test_file_2_out_descending,
                test_file_2_in1,
                test_file_2_in2,
                test_file_2_in3};

        MergeSortFile test4 = new MergeSortFile(array_test_2_descending);
        test4.run();

        Assertions.assertTrue(isEqual(file_2_out_descending.toPath(), file_2_result_descending.toPath()));

        //Папка test_file_3, в данном файле находятся строковый тип

        String path_test_3 = "C:/Users/ben70/IdeaProjects/CFT/src/cft/test/zuban/sort_merge/test/test_file_3/";

        String test_file_3_in1 = path_test_3 + "test_file_3_in1.txt";
        String test_file_3_in2 = path_test_3 + "test_file_3_in2.txt";
        String test_file_3_in3 = path_test_3 + "test_file_3_in3.txt";

        String test_file_3_out_ascending = path_test_3 + "test_file_3_out_ascending.txt";
        String test_file_3_out_descending = path_test_3 + "test_file_3_out_descending.txt";


        File file_3_out_ascending = new File(test_file_3_out_ascending);
        File file_3_out_descending = new File(test_file_3_out_descending);

        File file_3_result_ascending = new File(path_test_3 + "test_file_3_result_ascending.txt");
        File file_3_result_descending = new File(path_test_3 + "test_file_3_result_descending.txt");

        String[] array_test_3_ascending = {"-s", "-a",
                test_file_3_out_ascending,
                test_file_3_in1,
                test_file_3_in2,
                test_file_3_in3};

        MergeSortFile test5 = new MergeSortFile(array_test_3_ascending);
        test5.run();

        Assertions.assertTrue(isEqual(file_3_out_ascending.toPath(), file_3_result_ascending.toPath()));

        String[] array_test_3_descending = {"-s", "-d",
                test_file_3_out_descending,
                test_file_3_in1,
                test_file_3_in2,
                test_file_3_in3};

        MergeSortFile test6 = new MergeSortFile(array_test_3_descending);
        test6.run();

        Assertions.assertTrue(isEqual(file_3_out_descending.toPath(), file_3_result_descending.toPath()));

        //Папка test_file_4, в данном файле находятся строковый тип
        //В начале файла test_file_4_in1.txt и test_file_4_in2.txt идет пустое пространство

        String path_test_4 = "C:/Users/ben70/IdeaProjects/CFT/src/cft/test/zuban/sort_merge/test/test_file_4/";

        String test_file_4_in1 = path_test_4 + "test_file_4_in1.txt";
        String test_file_4_in2 = path_test_4 + "test_file_4_in2.txt";
        String test_file_4_in3 = path_test_4 + "test_file_4_in3.txt";

        String test_file_4_out_ascending = path_test_4 + "test_file_4_out_ascending.txt";
        String test_file_4_out_descending = path_test_4 + "test_file_4_out_descending.txt";


        File file_4_out_ascending = new File(test_file_4_out_ascending);
        File file_4_out_descending = new File(test_file_4_out_descending);

        File file_4_result_ascending = new File(path_test_4 + "test_file_4_result_ascending.txt");
        File file_4_result_descending = new File(path_test_4 + "test_file_4_result_descending.txt");

        String[] array_test_4_ascending = {"-s", "-a",
                test_file_4_out_ascending,
                test_file_4_in1,
                test_file_4_in2,
                test_file_4_in3};

        MergeSortFile test7 = new MergeSortFile(array_test_4_ascending);
        test7.run();

        Assertions.assertTrue(isEqual(file_4_out_ascending.toPath(), file_4_result_ascending.toPath()));

        String[] array_test_4_descending = {"-s", "-d",
                test_file_4_out_descending,
                test_file_4_in1,
                test_file_4_in2,
                test_file_4_in3};

        MergeSortFile test8 = new MergeSortFile(array_test_4_descending);
        test8.run();

        Assertions.assertTrue(isEqual(file_4_out_descending.toPath(), file_4_result_descending.toPath()));
    }

    private static boolean isEqual(Path firstFile, Path secondFile) {
        try {
            long size = Files.size(firstFile);
            if (size != Files.size(secondFile)) {
                return false;
            }

            if (size < 3048) {
                return Arrays.equals(Files.readAllBytes(firstFile),
                        Files.readAllBytes(secondFile));
            }

            try (BufferedReader bf1 = Files.newBufferedReader(firstFile);
                 BufferedReader bf3 = Files.newBufferedReader(secondFile)) {

                String line;
                while ((line = bf1.readLine()) != null) {
                    if (line.equals(bf3.readLine())) {
                        return false;
                    }
                }
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}