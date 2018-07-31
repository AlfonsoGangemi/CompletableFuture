package it.gangemi.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class MainClass {

    private static Logger logger = Logger.getLogger(MainClass.class.getName());

    public static void main(String[] args) {

        logger.fine("START");

        double a = 5, b = 3;

        CompletableFuture.supplyAsync(() -> {
                    logger.info("a = " + a);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return a * a;
                }
        ).thenApply(k -> {
            logger.info("a^2 = " + k);
            return k;
        }).thenCombineAsync(
                CompletableFuture.supplyAsync(() -> {
                            logger.info("b = " + b);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return b * b;
                        }
                ).thenApply(k -> {
                    logger.info("b^2 = " + k);
                    return k;
                }),
                (x, y) -> {
                    double z = x + y;
                    logger.info("waiting... (c^2 = " + z + ")");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return Math.sqrt(z);
                }
        ).thenAccept(k -> logger.info("c = " + k)).join();
        logger.fine("END");
    }
}
