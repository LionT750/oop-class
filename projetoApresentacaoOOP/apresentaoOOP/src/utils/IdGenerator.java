package utils;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static final AtomicLong productCounter = new AtomicLong(1);
    private static final AtomicLong offerCounter = new AtomicLong(1);

    public static long nextProductId() {
        return productCounter.getAndIncrement();
    }

    public static long nextOfferId() {
        return offerCounter.getAndIncrement();
    }
}
