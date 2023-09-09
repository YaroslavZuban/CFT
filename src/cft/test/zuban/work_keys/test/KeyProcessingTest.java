package cft.test.zuban.work_keys.test;

import cft.test.zuban.comparer.*;
import cft.test.zuban.work_keys.KeyProcessing;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KeyProcessingTest {

    @Test
    void identifyTypeSortAndData() {
        KeyProcessing keyProcessing = new KeyProcessing();

        List<String> list1 = Arrays.asList("-s", "out.txt", "in1.txt");
        assertTrue(keyProcessing.identifyTypeSortAndData(list1) instanceof IncreaseStringComparer);

        List<String> list2 = Arrays.asList("-i", "out.txt", "in1.txt");
        assertTrue(keyProcessing.identifyTypeSortAndData(list2) instanceof IncreaseNumberComparer);

        List<String> list3 = Arrays.asList("-s", "-a", "out.txt", "in1.txt");
        assertTrue(keyProcessing.identifyTypeSortAndData(list3) instanceof IncreaseStringComparer);

        List<String> list4 = Arrays.asList("-s", "-d", "out.txt", "in1.txt");
        assertTrue(keyProcessing.identifyTypeSortAndData(list4) instanceof DecreaseStringComparer);

        List<String> list5 = Arrays.asList("-i", "-a", "out.txt", "in1.txt");
        assertTrue(keyProcessing.identifyTypeSortAndData(list5) instanceof IncreaseNumberComparer);

        List<String> list6 = Arrays.asList("-i", "-d", "out.txt", "in1.txt");
        assertTrue(keyProcessing.identifyTypeSortAndData(list6) instanceof DecreaseNumberComparer);

        List<String> list7 = Arrays.asList("-a", "-i", "out.txt", "in1.txt");
        assertTrue(keyProcessing.identifyTypeSortAndData(list7) instanceof IncreaseNumberComparer);

        List<String> list8 = Arrays.asList("-d", "-i", "out.txt", "in1.txt");
        assertTrue(keyProcessing.identifyTypeSortAndData(list8) instanceof DecreaseNumberComparer);

        List<String> list9 = Arrays.asList("-a", "-s", "out.txt", "in1.txt");
        assertTrue(keyProcessing.identifyTypeSortAndData(list9) instanceof IncreaseStringComparer);

        List<String> list10 = Arrays.asList("-d", "-s", "out.txt", "in1.txt");
        assertTrue(keyProcessing.identifyTypeSortAndData(list10) instanceof DecreaseStringComparer);
    }
}