package nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * NIO读文件，从例子可以得知与传统的流操作对比，NIO直接使用channel接受ByteBuffer
 * 而BIO的方式则是用Buffer包装原生流将字节读取变成按缓冲区（块进行读取），NIO的方式显然更为优雅也更为直接
 */
public class NIOFileInputStream {

    public static void main(String[] args) {
        FileInputStream fis = null;
        FileChannel channel = null;
        try {

            fis = new FileInputStream("C:\\Users\\daive\\Desktop\\1.txt");
            channel = fis.getChannel();


            /*  将文件加锁
            FileLock fileLock = channel.lock();
            fileLock.release();*/
            //将文件映射到内存
            /*MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_ONLY,0,(int) channel.size());*/

            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());

            //读取完到byteBuffer后它的position已经改变
            channel.read(byteBuffer);

            /*Charset charset = Charset.forName("GBK");
            charset.decode(byteBuffer);*/
            //所以需要调用rewind将position重置为0开始读取 flip则是用于当读取完后要写入时使用
            byteBuffer.rewind();

            while (byteBuffer.hasRemaining()) {
                byte b = byteBuffer.get();
                System.out.println((char) b);
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}
