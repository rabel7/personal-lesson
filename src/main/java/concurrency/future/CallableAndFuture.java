package concurrency.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class CallableAndFuture {

    public static void main(String[] args) {
        Callable<Integer> callable = new Callable<Integer>() {
            public Integer call() throws Exception {
                Thread.sleep(2000);
                return new Random().nextInt(100);
            }
        };
        FutureTask<Integer> future = new FutureTask<>(callable);
        new Thread(future).start();
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}
