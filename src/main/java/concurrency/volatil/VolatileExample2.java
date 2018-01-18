package concurrency.volatil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 去掉 num变量的volatile修饰就可以直到volatile的作用
 * 当每次对volatile修饰的变量做修改时都会马上写入到主内存中
 * 那么当别的线程读取该变量的时候每次都会去主内存取最新的值
 *
 * 如果没有用volatile修饰变量，每个线程都有自己的暂，都会将变量
 * 的成员变量缓存copy一份线程临时变量（cpu缓存）中，因为在cpu存取速度更快，当线程
 * 结束后才会写入到主内存中。
 *
 * 基于以上，volatile修饰则强制线程每次写入和读取都是从主内存，保证了变量的可见性（对其他线程可见）
 * 注：当然唯独一个volatile并不能保证线程安全，因为它不能保证原子性。
 *
 * 注2：
 */
public class VolatileExample2 {

    public boolean isStop = false;

    public void shutdown(boolean isShutdown) {
        System.out.println(Thread.currentThread().getName()  + " 关机");
        isStop = isShutdown;
    }

    public boolean getIsStop() {
        return this.isStop;
    }

    public static void main(String[] args) throws InterruptedException {


        VolatileExample2 volatileExample = new VolatileExample2();
        Thread t1 = new Thread(new Test(volatileExample));
//        Thread t2 = new Thread(new Test2(volatileExample));

        t1.start();
//        t2.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Stop Thread");
        volatileExample.isStop = true;
    }



    static class Test implements Runnable {

        private VolatileExample2 volatileExample2;


        Test(VolatileExample2 volatileExample) {
            this.volatileExample2 = volatileExample;
        }

        @Override
        public void run() {

            while (!volatileExample2.isStop) {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

//    static class Test2 implements Runnable {
//
//        private VolatileExample2 volatileExample2;
//
//
//        Test2(VolatileExample2 volatileExample) {
//            this.volatileExample2 = volatileExample;
//        }
//
//        @Override
//        public void run() {
//
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    volatileExample2.shutdown(true);
//
//                }
//
//
//
//        }
//    }


}
