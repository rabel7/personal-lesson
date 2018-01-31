package concurrency.volatil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
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
public class VolatileExample {

    public int num = 0;

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {


        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        VolatileExample volatileExample = new VolatileExample();
        Thread t1 = new Thread(new Test(volatileExample, cyclicBarrier));
        Thread t2 = new Thread(new Test(volatileExample, cyclicBarrier));

        t1.start();
        t2.start();
    }

    public int getNum() {
        return num;
    }

    /**
     * 同步块新增，不需要使用volatile修饰
     * 在同步块内修改都会写入到主内存，读取也会从主内存读取
     * @return
     */
    public synchronized int syncIncreaseNum() {
        System.out.println(Thread.currentThread().getName() + " 获取num：" + this.num);
        System.out.println(Thread.currentThread().getName() + " 修改num：" + (++num));
        return num;
    }

    public int increaseLockNum() {

//        System.out.println(Thread.currentThread().getName() + " 获取num：" + this.num);
//        lock.lock();
        System.out.println(Thread.currentThread().getName() + " 修改num：" + (++num));
//        lock.unlock();
        return num;
    }

    static class Test implements Runnable {

        private VolatileExample volatileExample;

        private CyclicBarrier cyclicBarrier;

        Test(VolatileExample volatileExample, CyclicBarrier cyclicBarrier) {
            this.volatileExample = volatileExample;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                volatileExample.syncIncreaseNum();
                volatileExample.increaseLockNum();

            }


        }
    }


}
