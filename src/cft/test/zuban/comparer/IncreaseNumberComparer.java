package cft.test.zuban.comparer;

public class IncreaseNumberComparer implements Compare {
    @Override
    public Boolean isNextValid(String line1, String line2) {
        return Integer.parseInt(line1) > Integer.parseInt(line2);
    }
}