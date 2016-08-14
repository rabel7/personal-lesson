package io;

import java.io.*;

/**
 * Data streams support binary I/O of primitive data type values
 * (boolean, char, byte, short, int, long, float, and double) as well as String values
 * <p>
 * 注意
 * 1.如果尝试读取文件的末尾，则要捕获异常EOFException
 * 2.方法要一一对应，比如你用writeInt 那么就要 readInt
 * Notice that DataStreams detects an end-of-file condition by catching EOFException,
 * Also notice that each specialized write in DataStreams is exactly matched by the corresponding specialized read.
 * http://docs.oracle.com/javase/tutorial/essential/io/datastreams.html
 */
public class DataStream {
    static String basePath = "C:\\Users\\Administrator\\Desktop\\";
    static String dataFile = basePath + "invoicedata.txt";

    static final double[] prices = {19.99, 9.99, 15.99, 3.99, 4.99};
    static final int[] units = {12, 8, 13, 29, 50};
    static final String[] descs = {
            "Java T-shirt",
            "Java Mug",
            "Duke Juggling Dolls",
            "Java Pin",
            "Java Key Chain"
    };

    public static void main(String args[]) {
        try {
            File f = new File(dataFile);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)));

            for (int i = 0; i < prices.length; i++) {
                out.writeDouble(prices[i]);
                out.writeInt(units[i]);
                out.writeUTF(descs[i]);
            }

            out.close();

        } catch (IOException e) {
            e.printStackTrace(); // used to be System.err.println();
        }

        double price;
        int unit;
        String desc;
        double total = 0.0;

        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)));

            //如果这边使用true,则当循环一遍结束后再到price = in.readDouble();则会抛出EOFException
            while (in.available() > 0) {
                price = in.readDouble();
                unit = in.readInt();
                desc = in.readUTF();
                System.out.format("You ordered %d" + " units of %s at $%.2f%n",
                        unit, desc, price);
                total += unit * price;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.format("Your total is %f.%n", total);
    }
}
