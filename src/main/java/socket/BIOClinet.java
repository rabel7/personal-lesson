package socket;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 2016/7/9.
 */
public class BIOClinet {

    public static void main(String[] args) throws IOException {

        while(true){
            try {
                Socket socket = new Socket("127.0.0.1", 8888);

                PrintWriter outputStream = new PrintWriter(socket.getOutputStream());
                outputStream.print("测试");
                outputStream.print("戴翔");
                outputStream.println("byebye");

                outputStream.close();
                socket.close();

//                outputStream.print("aaa");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
