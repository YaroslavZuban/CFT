package cft.test.zuban.work_keys;

import java.util.List;

import cft.test.zuban.comparer.*;

public class KeyProcessing {
    public Compare identifyTypeSortAndData(List<String> argsList) {
        Compare comparer = null;

        if (argsList.contains("-i")) {
            if (argsList.contains("-d")) {
                comparer = new DecreaseNumberComparer();
            } else {
                comparer = new IncreaseNumberComparer();
            }
        } else if (argsList.contains("-s")) {
            if (argsList.contains("-d")) {
                comparer = new DecreaseStringComparer();
            } else {
                comparer = new IncreaseStringComparer();
            }
        } else {
            System.out.println("Не указали тип данных (-s или -i)");
            System.exit(0);
        }

        return comparer;
    }
}