package async;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by daive on 2016/7/31.
 */
public class Future {

    public static void main(String[] args) {

        threadPoolFuture();

        Callable<Integer> callable = new Callable<Integer>() {
            public Integer call() throws Exception {
                Thread.sleep(1000);
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

    }

    /**
     * 多个返回值
     * 此方法不保证顺序执行，如果要顺序执行
     * 先创建一个装Future类型的集合，用Executor提交的任务返回值添加到集合中，最后遍历集合取出数据
     */
    public static void threadPoolFuture() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> cs = new ExecutorCompletionService<>(threadPool);
        for (int i = 1; i < 5; i++) {
            final int taskID = i;
            cs.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    return new Random().nextInt(10000);
                }
            });
        }

        for (int i = 1; i < 5; i++) {
            try {
                System.out.println(cs.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }
}
