package cft.test.zuban.comparer;

public class IncreaseStringComparer implements Compare {
    @Override
    public Boolean isNextValid(String line1, String line2) {
        return line1.compareTo(line2) >= 0;
    }
}