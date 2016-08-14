package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 字节流 -> 一个字节一个字节的读, 1byte = 8bit
 * http://docs.oracle.com/javase/tutorial/essential/io/bytestreams.html
 */
public class ByteStream {
    public static void main(String[] args) throws IOException {

        FileInputStream in = null;
        FileOutputStream out = null;

        String basePath = "C:\\Users\\Administrator\\Desktop\\";
        try {
            in = new FileInputStream(basePath + "xanadu.txt");
            out = new FileOutputStream(basePath + "outagain.txt");
            int c;

            while ((c = in.read()) != -1) {
                //ascii 的对应字符
                System.out.println(c);
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}