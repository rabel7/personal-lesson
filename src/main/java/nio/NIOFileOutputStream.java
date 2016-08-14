package nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO写文件
 */
public class NIOFileOutputStream {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\daive\\Desktop\\1.txt");

        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("戴翔".getBytes());
        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        fileChannel.close();

        fileOutputStream.close();


    }
}
