package concurrency.cyclicbarrier;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  cyclicBarrier类似一条起跑线，只有等所有的选手（8个选手）都准备就绪的时候才会开枪起跑
 *  CountDownLatch 就好比一个终点线，只有当所有的选手（8个选手）都到达终点（计数为0）才会开始后续的名次统计
 *
 *  cyclicBarrier 设置一个屏障点，当线程到达该屏障点则挂起，只有所有线程都到达后才唤醒所有的线程
 *  当某个工作需要所有线程都执行完毕才可以开始时可以使用
 *
 *  比如 统计每个并发线程的调用,当所有的并发线程都结束后统计结果
 *
 *  CountDownLatch强调一个线程等多个线程完成某件事情。
 *  CyclicBarrier是多个线程互等，等大家都完成。
 *
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();



        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        CountDownLatch countDownLatch = new CountDownLatch(3);

        Worker w1 = new Worker(cyclicBarrier, countDownLatch,"张三");
        Worker w2 = new Worker(cyclicBarrier, countDownLatch,"李四");
        Worker w3 = new Worker(cyclicBarrier, countDownLatch,"王二");

        Boss boss = new Boss(cyclicBarrier, countDownLatch);

        executor.execute(w3);
        executor.execute(w2);
        executor.execute(w1);



        executor.execute(boss);

        executor.shutdown();
    }
}
