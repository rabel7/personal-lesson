package concurrency.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class Worker implements Runnable {
    private CyclicBarrier cyclicBarrier;
    private CountDownLatch countDownLatch;
    private String name;

    public Worker(CyclicBarrier cyclicBarrier, CountDownLatch countDownLatch, String name){
        this.cyclicBarrier = cyclicBarrier;
        this.countDownLatch = countDownLatch;
        this.name = name;
    }

    public void run() {
        //模拟其他准备工作
        try{
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        }catch(InterruptedException ie){
        }


        try {
            System.out.println(this.name + "准备好开始干活了,还要等待" + (cyclicBarrier.getNumberWaiting() + 1) + "人才能开始干活");
            this.cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        this.doWork();
        countDownLatch.countDown();
    }

    private void doWork(){
        System.out.println(this.name + "正在干活!");
    }
}
