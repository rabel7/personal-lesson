package concurrency.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor构造函数几个参数说明
 *  核心池大小 corePoolSize the number of threads to keep in the pool, even
 *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
 *
 *  最大池大小 maximumPoolSize the maximum number of threads to allow in the
 *        pool
 *  保持存活时间 keepAliveTime when the number of threads is greater than
 *        the core, this is the maximum time that excess idle threads
 *        will wait for new tasks before terminating.
 *  参数keepAliveTime的时间单位 unit the time unit for the {@code keepAliveTime} argument
 *  阻塞队列 workQueue the queue to use for holding tasks before they are
 *        executed.  This queue will hold only the {@code Runnable}
 *        tasks submitted by the {@code execute} method.
 *  线程工厂 threadFactory the factory to use when the executor
 *        creates a new thread
 *  表示当拒绝处理任务时的策略 handler：the handler to use when execution is blocked
 *  because the thread bounds and queue capacities are reached
     ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
     ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
     ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
     ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务

 *  重要方法说明
 *  execute()方法实际上是接口Executor中声明的方法，在ThreadPoolExecutor进行了具体的实现，
 *  这个方法是ThreadPoolExecutor的核心方法，通过这个方法可以向线程池提交一个任务，交由线程池去执行。
 *
 *  submit()方法是在ExecutorService中声明的方法，在AbstractExecutorService就已经有了具体的实现。
 *  在ThreadPoolExecutor中并没有对其进行重写，这个方法也是用来向线程池提交任务的，但是它和execute()方法不同，
 *  它能够返回任务执行的结果，去看submit()方法的实现，会发现它实际上还是调用的execute()方法，
 *  只不过它利用了Future来获取任务执行结果（Future相关内容将在下一篇讲述）。
 *
 *  shutdown()和shutdownNow()是用来关闭线程池的。

    故事说明：
    每个变量的作用都已经标明出来了，这里要重点解释一下corePoolSize、maximumPoolSize、largestPoolSize三个变量。

 　　corePoolSize在很多地方被翻译成核心池大小，其实我的理解这个就是线程池的大小。举个简单的例子：

 　　假如有一个工厂，工厂里面有10个工人，每个工人同时只能做一件任务。

 　　因此只要当10个工人中有工人是空闲的，来了任务就分配给空闲的工人做；

 　　当10个工人都有任务在做时，如果还来了任务，就把任务进行排队等待；

 　　如果说新任务数目增长的速度远远大于工人做任务的速度，那么此时工厂主管可能会想补救措施，比如重新招4个临时工人进来；

 　　然后就将任务也分配给这4个临时工人做；

 　　如果说着14个工人做任务的速度还是不够，此时工厂主管可能就要考虑不再接收新的任务或者抛弃前面的一些任务了。

 　　当这14个工人当中有人空闲时，而新任务增长的速度又比较缓慢，工厂主管可能就考虑辞掉4个临时工了，只保持原来的10个工人，毕竟请额外的工人是要花钱的。

 　　这个例子中的corePoolSize就是10，而maximumPoolSize就是14（10+4）。

 　　也就是说corePoolSize就是线程池大小，maximumPoolSize在我看来是线程池的一种补救措施，即任务量突然过大时的一种补救措施。

     largestPoolSize只是一个用来起记录作用的变量，用来记录线程池中曾经有过的最大线程数目，跟线程池的容量没有任何关系。

 http://www.cnblogs.com/dolphin0520/p/3932921.html
 */
public class ThreadPool {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));

        for (int i = 0; i < 15; i++) {

            MyTask myTask = new MyTask(i);

            executor.execute(myTask);

            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +

                    executor.getQueue().size() + "，已执行完任务数目：" + executor.getCompletedTaskCount());

        }
        executor.shutdown();

        //java doc建议如果这三个静态方法符合则直接使用，不推荐使用new的方式
        Executors.newCachedThreadPool();        //创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE

        Executors.newSingleThreadExecutor();   //创建容量为1的缓冲池

        Executors.newFixedThreadPool(100);    //创建固定容量大小的缓冲池
    }

}
