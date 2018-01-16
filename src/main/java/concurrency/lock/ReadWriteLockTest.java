package concurrency.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁可以将读写分离，读的时候可以并发读，写的时候只能写且不能读，只有当写锁释放了才允许读和写
 * 写锁是独占锁
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        //创建并发访问的账户
        MyCount myCount = new MyCount("95599200901215522", 10000);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7);
        //创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(7);
        //创建一些并发访问用户，一个信用卡，存的存，取的取，好热闹啊
        User u1 = new User("张三", myCount, -4000, cyclicBarrier, false);
        User u2 = new User("张三他爹", myCount, 6000, cyclicBarrier, false);
        User u3 = new User("张三他弟", myCount, -8000, cyclicBarrier, false);
        User u4 = new User("张三", myCount, 800, cyclicBarrier, false);
        User u5 = new User("张三他爹", myCount, 0, cyclicBarrier, true);
        User u6 = new User("张三他弟", myCount, 0, cyclicBarrier, true);
        User u7 = new User("张三", myCount, 0, cyclicBarrier, true);

        //在线程池中执行各个用户的操作
        pool.execute(u6);
        pool.execute(u1);
        pool.execute(u2);
        pool.execute(u3);
        pool.execute(u4);
        pool.execute(u5);
        pool.execute(u7);

        //关闭线程池
        pool.shutdown();
    }


    static class User implements Runnable{

        private String name;                 //用户名
        private MyCount myCount;         //所要操作的账户
        private int iocash;                 //操作的金额，当然有正负之分了
        private static ReadWriteLock myLock =  new ReentrantReadWriteLock();                 //执行操作所需的锁对象
        private boolean ischeck;         //是否查询
        private CyclicBarrier cyclicBarrier;

        User(String name, MyCount myCount, int iocash, CyclicBarrier cyclicBarrier, boolean ischeck) {
            this.name = name;
            this.myCount = myCount;
            this.iocash = iocash;
            this.ischeck = ischeck;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                this.cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            if (ischeck) {
                //获取读锁
                myLock.readLock().lock();
                System.out.println(Thread.currentThread().getName() +  " 读：" + name + "正在查询" + myCount + "账户，当前金额为" + myCount.getCash());
                //在两个print中加入sleep，证明读是可以并发读的
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() +  " 读：" + name + "查询" + myCount + "账户成功，当前金额为" + myCount.getCash());
                //释放读锁
                myLock.readLock().unlock();
            } else {
                //获取写锁
                myLock.writeLock().lock();
                //执行现金业务
                System.out.println(Thread.currentThread().getName() + " 准备写：" + name + "正在操作" + myCount + "账户，金额为" + iocash +"，当前金额为" + myCount.getCash());
                myCount.setCash(myCount.getCash() + iocash);
                //在两个print中加入sleep，证明读是可以并发读的
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 写成功：" + name + "操作" + myCount + "账户成功，金额为" + iocash +"，当前金额为" + myCount.getCash());
                //释放写锁
                myLock.writeLock().unlock();
            }
        }
    }


    static class MyCount {

        private String oid;         //账号
        private int cash;             //账户余额

        MyCount(String oid, int cash) {
            this.oid = oid;
            this.cash = cash;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public int getCash() {
            return cash;
        }

        public void setCash(int cash) {
            this.cash = cash;
        }

        @Override
        public String toString() {
            return "MyCount{" +
                    "oid='" + oid + '\'' +
                    ", cash=" + cash +
                    '}';
        }

    }
}


