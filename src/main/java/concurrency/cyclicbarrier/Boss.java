package concurrency.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by daixiang on 2018/1/13.
 */
public class Boss implements Runnable {

    private CyclicBarrier cyclicBarrier;

    private CountDownLatch countDownLatch;

    public Boss(CyclicBarrier cyclicBarrier, CountDownLatch countDownLatch){
        this.cyclicBarrier = cyclicBarrier;
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("工人活都干完了，老板开始检查了！");
    }

}