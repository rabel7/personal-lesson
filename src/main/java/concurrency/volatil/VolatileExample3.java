package concurrency.volatil;

import java.util.concurrent.TimeUnit;

public class VolatileExample3 {
    private static /*volatile*/ boolean stop = false;
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while (!stop) {
                    i++;
//                    System.out.println("hello");
                }
            }
        });
        t.start();

        Thread.sleep(1000);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Stop Thread");
        stop = true;
    }
}
