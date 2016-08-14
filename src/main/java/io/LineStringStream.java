package io;

import java.io.*;

/**
 * 按行的单位来读取
 * http://docs.oracle.com/javase/tutorial/essential/io/charstreams.html
 * Buffered与其他流的不同在于，其他的流每次读取和写入都要消耗系统的io
 * 而buffered顾名思义，缓存区，我们可以将字符先写入缓冲区，当缓冲区满后再一次性写入
 * 如果要使用buffered之需要再进行一层包装即可，比如19行
 * buffered有个flush方法，意思是说将缓冲区内的数据写入到流，可以在缓冲区未满的情况下调用
 * 注：但也有些类是支持自动flush的，在一些事件调用的情况下，比如printWriter在调用 println or format时会自动调用
 * http://docs.oracle.com/javase/tutorial/essential/io/buffers.html
 */
public class LineStringStream {
    public static void main(String[] args) throws IOException {

        BufferedReader inputStream = null;
        PrintWriter outputStream = null;
        String basePath = "C:\\Users\\Administrator\\Desktop\\";

        try {
            inputStream = new BufferedReader(new FileReader((basePath + "xanadu.txt")));
            outputStream = new PrintWriter(new FileWriter(basePath + "characteroutput.txt"));

            String l;
            while ((l = inputStream.readLine()) != null) {
                System.out.println(l);
                outputStream.println(l);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
