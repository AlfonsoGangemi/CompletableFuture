# CompletableFuture
Check behavior for CompletableFuture sync/async 

## Result
<pre>
FINE[11:42:58]#> START
INFO[11:42:58]#> a = 5.0
INFO[11:42:58]#> b = 3.0
INFO[11:42:59]#> b^2 = 9.0
INFO[11:43:03]#> a^2 = 25.0
INFO[11:43:03]#> waiting... (c^2 = 34.0)
INFO[11:43:06]#> c = 5.830951894845301
FINE[11:43:06]#> END
</pre>

## increment
increment AtomicInteger with CompletableFuture
 (there is a simple Progress indicator)