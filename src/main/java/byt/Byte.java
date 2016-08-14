package byt;

import java.io.UnsupportedEncodingException;

/**
 * 证明在不同的编码下中文占的字节数不相同，但英文都占一个字节
 * Java中的char默认采用Unicode编码，所以Java中char占2个字节
 */
public class Byte {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //unicode 中文占三个字节
        String str = "戴x";
        //unicode 长度为4 中文2个字节 英文2个
        System.out.println("unicode:" + str.getBytes("unicode").length);
        //UTF-8 长度为4 中文3个字节 英文1个
        System.out.println(System.getProperty("file.encoding") + ":" + str.getBytes().length);
        //GBK 长度为3 中文2个字节 英文1个
        System.out.println("GBK:" + str.getBytes("GBK").length);
        //ISO-8859-1 长度为2 中文1个字节 英文1个
        System.out.println("ISO-8859-1:" + str.getBytes("ISO-8859-1").length);
        //GBK2312长度为3 中文2个字节 英文1个
        System.out.println("GB2312:" + str.getBytes("GB2312").length);
        //ISO-8859-1 长度为6 中文4个字节 英文2个
        System.out.println("UTF-16:" + str.getBytes("UTF-16").length);


        byte b = 'a';
        byte a = 97;
        System.out.println(b);
        System.out.println(a);
    }
}
