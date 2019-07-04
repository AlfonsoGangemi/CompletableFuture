package it.gangemi.concurrent.increment;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class MainClass {

    private static final int SIZE = 1000;
    private static AtomicInteger inc = new AtomicInteger(0);
    private static SimpleProgress progress = new SimpleProgress(SIZE);

    public static void main(String[] args) {
        Instant start = Instant.now();
        List<CompletableFuture<Void>> futureArrayList = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            final int finalI = i;
            futureArrayList.add(CompletableFuture.runAsync(() -> {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        inc.incrementAndGet();
                        System.out.print(progress.step(finalI, "CompletableFuture#" + finalI));
                    })
            );
        }
        CompletableFuture.allOf(futureArrayList.toArray(new CompletableFuture[]{})).join();
        System.out.println("\rValoreFinale:" + inc.get() + " tempo(ms):" + (Duration.between(start, Instant.now())).toMillis());
    }

}
