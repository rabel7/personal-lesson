package collection.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞有界队列
 * 线程安全
 * 生产消费模型
 * 当没有得消费则生产者阻塞，当生产满了生产者阻塞
 *
 */
public class ArrayBlockingQueueTest {


    public static void main(String[] args) throws InterruptedException {

        final BlockingQueue queue = new ArrayBlockingQueue<ArrayBlockingQueueTest>(5);

        Runnable productRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        System.out.println(Thread.currentThread().getName() + "生产中...");
                        queue.put(new ArrayBlockingQueueTest());
                        System.out.println(Thread.currentThread().getName() + "生产完毕, 剩余：" + queue.remainingCapacity());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable consumerRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        System.out.println(Thread.currentThread().getName() + "消费中...");
                        ArrayBlockingQueueTest queueTest = (ArrayBlockingQueueTest) queue.take();
                        System.out.println(Thread.currentThread().getName() + "消费完毕, 剩余：" + queue.remainingCapacity());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread product1 = new Thread(productRunnable, "product1-thread");
        Thread product2 = new Thread(productRunnable, "product2-thread");

        Thread consumer1 = new Thread(consumerRunnable, "consumer1-thread");


        consumer1.start();

        product1.start();
        product2.start();


    }
}
