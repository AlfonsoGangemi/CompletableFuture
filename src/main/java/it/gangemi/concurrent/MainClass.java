package it.gangemi.concurrent;

import java.util.concurrent.CompletableFuture;

public class MainClass {

    public static void main(String[] args) {

        System.out.println("START");

        double a = 5, b = 3;

        CompletableFuture.supplyAsync(() -> {
                    System.out.println("a = " + a);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return a * a;
                }
        ).thenApply(k -> {
            System.out.println("a^2 = " + k);
            return k;
        }).thenCombineAsync(
                CompletableFuture.supplyAsync(() -> {
                            System.out.println("b = " + b);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return b * b;
                        }
                ).thenApply(k -> {
                    System.out.println("b^2 = " + k);
                    return k;
                }),
                (x, y) -> {
                    double z = x + y;
                    System.out.println("waiting... (c^2 = " + z + ")");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return Math.sqrt(z);
                }
        ).thenAccept(k -> System.out.println("c = " + k)).join();
        System.out.println("END");
    }
}
