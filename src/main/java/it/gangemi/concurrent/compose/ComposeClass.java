package it.gangemi.concurrent.compose;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ComposeClass {

    private CompletableFuture<List<StringBean>> stringBeanCompletableFuture = CompletableFuture.supplyAsync(
            () -> {
                int size = new Random().nextInt(10);
                try {
                    int sleepTime = new Random().nextInt(3000);
                    System.out.println("waiting stringBeanCompletableFuture... ^"+sleepTime);
                    Thread.sleep(sleepTime);
                    System.out.println("waiting stringBeanCompletableFuture... OK -> size:"+size);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<StringBean> result = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    result.add(StringBean.build(Integer.toString(i), String.valueOf((char)((int)'A'+i))));
                }
                return result;
            }
    );

    private CompletableFuture<List<LongBean>> longBeanCompletableFuture = CompletableFuture.supplyAsync(
            () -> {
                int size = new Random().nextInt(10);
                try {
                    int sleepTime = new Random().nextInt(3000);
                    System.out.println("waiting longBeanCompletableFuture... ^"+sleepTime);
                    Thread.sleep(sleepTime);
                    System.out.println("waiting longBeanCompletableFuture... OK -> size:"+size);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<LongBean> result = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    result.add(LongBean.build(Integer.toString(i), System.currentTimeMillis()));
                }
                return result;
            }
    );

    private static ComposeClass getInstance() {
        return new ComposeClass();
    }

    public static void main(String[] args) {
        final ComposeClass composeClass = getInstance();
        CompletableFuture<Map<String, ComplexBean>> mapCompletableFuture = composeClass.stringBeanCompletableFuture
                .thenCombine(composeClass.longBeanCompletableFuture,
                        (stringList, longList) -> {
                            final Map<String, ComplexBean> complexBeanMap = stringList.stream().collect(Collectors.toMap(StringBean::getKey, ComplexBean::build));
                            longList.forEach(
                                    //x -> complexBeanMap.get(x.getKey()).setValueLong(x.getValueLong()) //UNSAFE x NULL
                                    x -> {
                                        final String key = x.getKey();
                                        final ComplexBean complexBean = complexBeanMap.getOrDefault(key, ComplexBean.build(key));
                                        complexBean.setValueLong(x.getValueLong());
                                        complexBeanMap.put(key, complexBean);
                                    });
                            return complexBeanMap;
                        }
                );
        try {
            System.out.println(mapCompletableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }




}
