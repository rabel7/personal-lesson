package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 每接收一个请求从线程池中取出线程进行处理
 */
public class BIOThreadPoolServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        new BIOThreadPoolServer(8888).startServer();

    }

    private int port;

    private ExecutorService executorService;

    public BIOThreadPoolServer() {
        this(8888);
    }

    public BIOThreadPoolServer(int port) {
        this.port = port;
        executorService = Executors.newFixedThreadPool(20);
    }

    /**
     * start server accept any socket connect
     * muliti thread deal all connect
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void startServer() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {

            Socket accept = serverSocket.accept();

//            new Thread(new ServerSocketHandler(accept)).start();
            executorService.execute(new ServerSocketHandler(accept));

            Thread.sleep(10);
        }

    }

    class ServerSocketHandler implements Runnable {

        private Socket accept;

        ServerSocketHandler() {

        }

        ServerSocketHandler(Socket accept) {
            this.accept = accept;
        }

        @Override
        public void run() {
            BufferedReader inputStream = null;
            String line;

            try {
                inputStream = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                while ((line = inputStream.readLine()) != null) {
                    System.out.println(Thread.currentThread().getName() + "accept clinet:" + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (accept != null) {
                        accept.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
