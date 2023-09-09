package cft.test.zuban.main;

import cft.test.zuban.sort_merge.MergeSortFile;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new MergeSortFile(args).run();
    }
}