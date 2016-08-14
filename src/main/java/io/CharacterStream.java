package io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *  字符流 -> 看例子与字节流很类似，区别在于字符流是每2个字节读取
 *  Character I/O usually occurs in bigger units than single characters
 *  比如说有中文的时候使用字符流
 *  http://docs.oracle.com/javase/tutorial/essential/io/charstreams.html
 */
public class CharacterStream {

    public static void main(String[] args) throws IOException {

        FileReader inputStream = null;
        FileWriter outputStream = null;
        String basePath = "C:\\Users\\Administrator\\Desktop\\";

        try {
            inputStream = new FileReader(basePath + "xanadu.txt");
            outputStream = new FileWriter(basePath + "characteroutput.txt");

            int c;
            while ((c = inputStream.read()) != -1) {
                System.out.println(c);
                outputStream.write(c);
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
