package collection.queue;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 优先级队列，每次队列取出都按照优先级最高的原则
 * 比较是按照对象的compareTo方法进行比较
 */
public class PriorityQueueTest {


    public static void main(String[] args) {
//        Queue<Integer> priorityQueue = new PriorityQueue<>();
//        priorityQueue.add(1);
//        priorityQueue.add(4);
//        priorityQueue.add(2);
//        priorityQueue.add(3);
//
//        System.out.println(priorityQueue.poll());
//        System.out.println(priorityQueue.poll());


        Queue<FIFO> priorityQueue = new PriorityQueue<>();


        priorityQueue.add(new FIFO(10));
        priorityQueue.add(new FIFO(1));
        priorityQueue.add(new FIFO(100));
        priorityQueue.add(new FIFO(5));
        priorityQueue.add(new FIFO(7));
//        priorityQueue.add(new FIFO(0));

//        System.out.println(priorityQueue.size());
//        priorityQueue.add(new FIFO(0));

        FIFO f = priorityQueue.poll();
        f.increateNum();
        f.increateNum();
        f.increateNum();
        priorityQueue.add(f);


        System.out.println(priorityQueue.poll());
    }


}

class FIFO implements Comparable<FIFO>{
    //access num
    private int num;


    FIFO(int accessNum) {
        this.num = accessNum;
    }

    FIFO() {
        this(0);
    }

    public void increateNum() {
        num += 1;
    }

    public String toString() {
        return "FIFO num:" + num;
    }

    @Override
    public int compareTo(FIFO o) {
        return Integer.valueOf(num).compareTo(o.num);
    }
}