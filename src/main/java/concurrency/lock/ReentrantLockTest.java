package concurrency.lock;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  todo 线程中如何抛出异常
 *
 *
 *
 *
 *
 *
 *
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(new Test(cyclicBarrier));
            thread.start();
        }
    }


    static class Test implements Runnable{

        static Lock lock = new ReentrantLock();

        private CyclicBarrier cyclicBarrier;

        public Test(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }


        public void someMethod() throws InterruptedException, BrokenBarrierException {
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + " : enter someMethod");

            lock.lock();

            //some code
            int lockSeconds = new Random().nextInt(5);
            System.out.println(Thread.currentThread().getName() + " : get lock time:" + lockSeconds + "s");
            TimeUnit.SECONDS.sleep(lockSeconds);

            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " : release lock");
        }


        @Override
        public void run() {
            try {
                someMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
