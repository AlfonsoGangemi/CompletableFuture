package it.gangemi.concurrent.compose;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

/**
 * https://springorama.wordpress.com/2018/05/10/combining-merging-more-than-two-completablefutures-together/
 */
public class SimpleComposeClass {

    ForkJoinPool myThreadPool = new ForkJoinPool(10);

    CompletableFuture<Integer> myCompletableFutureInt = CompletableFuture.supplyAsync(() -> {
        try {
            int sleepTime = new Random().nextInt(2000);
            Thread.sleep(sleepTime);
            System.out.println(
                    "Sleeping for " + sleepTime + " in myCompletableFutureInt on thread "
                            + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextInt(50);
    }, myThreadPool);

    CompletableFuture<BigDecimal> myCompletableFutureBigDecimal = CompletableFuture
            .supplyAsync(() -> {
                try {
                    int sleepTime = new Random().nextInt(1000);
                    Thread.sleep(sleepTime);
                    System.out.println(
                            "Sleeping for " + sleepTime + " in myCompletableFutureBigDecimal on thread "
                                    + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new BigDecimal(new Random().nextDouble() * 50);
            }, myThreadPool);

    CompletableFuture<Long> myCompletableFutureLong = CompletableFuture.supplyAsync(() -> {
        try {
            int sleepTime = new Random().nextInt(1000);
            Thread.sleep(sleepTime);
            System.out.println(
                    "Sleeping for " + sleepTime + " in myCompletableFutureLong on thread "
                            + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextLong();
    }, myThreadPool);

    public static void main(String[] args) {
        SimpleComposeClass simpleComposeClass = new SimpleComposeClass();
        CompletableFuture<Map<String, Object>> myObjectCompletableFuture = simpleComposeClass.buildObject();
        try {
            Map<String, Object> myObjectMap = myObjectCompletableFuture.get(2, TimeUnit.SECONDS);
            myObjectMap.entrySet().forEach(stringObjectEntry -> System.out
                    .println(stringObjectEntry.getKey() + " = " + stringObjectEntry.getValue().toString()));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private CompletableFuture<Map<String, Object>> buildObject() {
        return myCompletableFutureBigDecimal.thenCompose(bigDecimalValue ->
                myCompletableFutureInt
                        .thenCombine(myCompletableFutureLong,
                                ((integerValue, longValue) -> {
                                    Map<String, Object> objectHashMap = new HashMap<>();
                                    objectHashMap.put("IntegerValue", integerValue);
                                    objectHashMap.put("LongValue", longValue);
                                    objectHashMap.put("BigDecimalValue", bigDecimalValue);
                                    return objectHashMap;
                                })));
    }

}
