package us.ajg0702.queue.api.util;

public class ExpressRatio {
    private final int express;
    private final int standard;

    private static final ExpressRatio ONE_TO_ONE = new ExpressRatio(1, 1);
    public static ExpressRatio oneToOne() {
        return ONE_TO_ONE;
    }

    public ExpressRatio(int express, int standard) {
        this.express = express;
        this.standard = standard;
    }

    public int getExpressCount() {
        return express;
    }
    public int getStandardCount() {
        return standard;
    }
}
