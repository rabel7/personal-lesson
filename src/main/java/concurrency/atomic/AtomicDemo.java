package concurrency.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by daixiang on 2018/1/11.
 */
public class AtomicDemo implements Runnable{

//    private final AtomicInteger atomicInteger = new AtomicInteger();

    private volatile int atomicInteger = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            increase();
        }

//        atomicInteger.incrementAndGet();
    }

    public void increase() {
//        atomicInteger.incrementAndGet();
        atomicInteger++;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void syncIncrease() {
//        atomicInteger++;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        AtomicDemo demo = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(demo);
            t.start();
//            try {
//                t.join();
//            }
//            catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        while (Thread.activeCount() > 1) {
//            Thread.yield();

        }

        System.out.println(Thread.currentThread().getName()+ "最终结果：" +  demo.atomicInteger);
    }
}
