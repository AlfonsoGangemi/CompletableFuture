package it.gangemi.concurrent.compose;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ComposeClass {

    private CompletableFuture<List<StringBean>> stringBeanCompletableFuture = CompletableFuture.supplyAsync(
            () -> {
                try {
                    int sleepTime = new Random().nextInt(2000);
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<StringBean> result = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    result.add(StringBean.build(Integer.toString(i), UUID.randomUUID().toString()));
                }
                return result;
            }
    );

    private CompletableFuture<List<LongBean>> longBeanCompletableFuture = CompletableFuture.supplyAsync(
            () -> {
                try {
                    int sleepTime = new Random().nextInt(2000);
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<LongBean> result = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    result.add(LongBean.build(Integer.toString(i), System.currentTimeMillis()));
                }
                return result;
            }
    );

    public static void main(String[] args) {
        ComposeClass composeClass = new ComposeClass();
        CompletableFuture<Map<String, Object>> mapCompletableFuture = composeClass.stringBeanCompletableFuture
                .thenCombine(composeClass.longBeanCompletableFuture, (s, l) -> {
                    Map<String, Object> objectHashMap = new HashMap<>();
                    objectHashMap.put("LongValues", l);
                    objectHashMap.put("StringValues", s);
            return objectHashMap;
        });
        try {
            System.out.println(mapCompletableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
