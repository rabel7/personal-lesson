package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * 每接收一个请求启动一个线程进行处理
 * 线程的建立太开销性能，这边优化方式使用线程池的形式
 */
public class BIOServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        new BIOServer(8888).startServer();

    }

    private int port;

    public BIOServer() {
        this(8888);
    }

    public BIOServer(int port) {
        this.port = port;
    }

    /**
     * start server accept any socket connect
     * muliti thread deal all connect
     * @throws IOException
     * @throws InterruptedException
     */
    public void startServer() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {

            Socket accept = serverSocket.accept();

            new Thread(new ServerSocketHandler(accept)).start();

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
