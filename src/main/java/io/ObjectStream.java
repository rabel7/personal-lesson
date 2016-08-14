package io;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * 就是对象的流啦
 * 不是所有对象都可以的，需要实现了序列化接口的对象
 * 注：会将所有引用写入
 * http://docs.oracle.com/javase/tutorial/essential/io/objectstreams.html
 */
public class ObjectStream {

    static String basePath = "C:\\Users\\Administrator\\Desktop\\";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(basePath + "object.txt"));

        objectOutputStream.writeObject(user);

        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(basePath + "object.txt"));

        Object object = objectInputStream.readObject();

        objectInputStream.close();

        User castUser = (User) object;

        System.out.println("age: " + castUser.age);
        System.out.println("name: " + castUser.name);
        System.out.println("salary: " + castUser.salary);
        System.out.println("phoneNumbers: " + castUser.phoneNumbers.toArray());
    }
}


class User implements Serializable {
    public int age = 1;
    public String name = "dx";
    public List<String> phoneNumbers = Arrays.asList("130", "131");
    public Double salary = new Double(2555.1);

}
