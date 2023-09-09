package cft.test.zuban.line_parser.test;

import cft.test.zuban.comparer.DecreaseNumberComparer;
import cft.test.zuban.comparer.DecreaseStringComparer;
import cft.test.zuban.comparer.IncreaseNumberComparer;
import cft.test.zuban.comparer.IncreaseStringComparer;
import cft.test.zuban.line_parser.ArgsParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArgsParserTest {

    @Test
    void searchKeys() {
        ArgsParser argsParser = new ArgsParser();
        String[] args1 = {"-s", "out.txt", "in1.txt"};
        List<String> list1Result = List.of("-s");
        assertTrue(list1Result.equals(argsParser.searchKeys(args1)));

        String[] args2 = {"-i", "out.txt", "in1.txt"};
        List<String> list2Result = List.of("-i");
        assertTrue(list2Result.equals(argsParser.searchKeys(args2)));

        String[] args3 = {"-s", "-a", "out.txt", "in1.txt"};
        List<String> list3Result = List.of("-s", "-a");
        assertTrue(list3Result.equals(argsParser.searchKeys(args3)));

        String[] args4 = {"-s", "-d", "out.txt", "in1.txt"};
        List<String> list4Result = List.of("-s", "-d");
        assertTrue(list4Result.equals(argsParser.searchKeys(args4)));

        String[] args5 = {"-i", "-a", "out.txt", "in1.txt"};
        List<String> list5Result = List.of("-i", "-a");
        assertTrue(list5Result.equals(argsParser.searchKeys(args5)));

        String[] args6 = {"-i", "-d", "out.txt", "in1.txt"};
        List<String> list6Result = List.of("-i", "-d");
        assertTrue(list6Result.equals(argsParser.searchKeys(args6)));

        String[] args7 = {"-a", "-i", "out.txt", "in1.txt"};
        List<String> list7Result = List.of("-a", "-i");
        assertTrue(list7Result.equals(argsParser.searchKeys(args7)));

        String[] args8 = {"-d", "-i", "out.txt", "in1.txt"};
        List<String> list8Result = List.of("-d", "-i");
        assertTrue(list8Result.equals(argsParser.searchKeys(args8)));

        String[] args9 = {"-a", "-s", "out.txt", "in1.txt"};
        List<String> list9Result = List.of("-a", "-s");
        assertTrue(list9Result.equals(argsParser.searchKeys(args9)));

        String[] args10 = {"-d", "-s", "out.txt", "in1.txt"};
        List<String> list10Result = List.of("-d", "-s");
        assertTrue(list10Result.equals(argsParser.searchKeys(args10)));
    }

    @Test
    void searchFiles() {
        ArgsParser argsParser = new ArgsParser();
        String[] args1 = {"-s", "out.txt", "in1.txt","in2.txt","in3.txt","in4.txt","in5.txt"};
        List<String> list1Result = List.of("out.txt", "in1.txt","in2.txt","in3.txt","in4.txt","in5.txt");
        assertTrue(list1Result.equals(argsParser.searchFiles(args1)));

        String[] args2 = {"-i", "out.txt", "in1.txt"};
        List<String> list2Result = List.of("out.txt", "in1.txt");
        assertTrue(list2Result.equals(argsParser.searchFiles(args2)));

        String[] args3 = {"-s", "-a", "out.txt", "in1.txt"};
        List<String> list3Result = List.of("out.txt", "in1.txt");
        assertTrue(list3Result.equals(argsParser.searchFiles(args3)));

        String[] args4 = {"-s", "-d", "out.txt"};
        List<String> list4Result = List.of( "out.txt");
        assertTrue(list4Result.equals(argsParser.searchFiles(args4)));

        String[] args5 = {"-i", "-a"};
        List<String> list5Result = List.of();
        assertTrue(list5Result.equals(argsParser.searchFiles(args5)));

        String[] args6 = {"-i", "-d", "out.txt", "in1.txt"};
        List<String> list6Result = List.of("out.txt", "in1.txt");
        assertTrue(list6Result.equals(argsParser.searchFiles(args6)));

        String[] args7 = {"-a", "-i", "out.txt", "in1.txt"};
        List<String> list7Result = List.of("out.txt", "in1.txt");
        assertTrue(list7Result.equals(argsParser.searchFiles(args7)));

        String[] args8 = {"-d", "-i", "out.txt", "in1.txt"};
        List<String> list8Result = List.of("out.txt", "in1.txt");
        assertTrue(list8Result.equals(argsParser.searchFiles(args8)));

        String[] args9 = {"-a", "-s", "out.txt", "in1.txt"};
        List<String> list9Result = List.of("out.txt", "in1.txt");
        assertTrue(list9Result.equals(argsParser.searchFiles(args9)));

        String[] args10 = {"-d", "-s", "out.txt", "in1.txt"};
        List<String> list10Result = List.of("out.txt", "in1.txt");
        assertTrue(list10Result.equals(argsParser.searchFiles(args10)));
    }
}