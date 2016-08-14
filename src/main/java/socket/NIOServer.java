package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 重要：与bio的区别只在于接收连接的时候是单线程，但是后续处理还是多线程的，只是说nio可以先排除掉无效连接
 *
 * 主线程接收连接，可以在接收连接后判断是否为有效连接，如果是的话再启动线程进行处理
 * NIO基于Reactor，当socket有流可读或可写入socket时，操作系统会相应的通知引用程序进行处理，应用再将流读取到缓冲区或写入操作系统。
 * 也就是说，这个时候，已经不是一个连接就要对应一个处理线程了，而是有效的请求，对应一个线程，当连接没有数据时，是没有工作线程来处理的
 *
 * Server端通常由一个thread来监听connect事件，另外多个thread来监听读写事件。这样做的好处是这些连接只有在真是请求的时候才会创建thread来处理，
 * one request one thread。这种方式在server端需要支持大量连接但这些连接同时发送请求的峰值不会很多的时候十分有效。
 *
 *  NIO的最重要的地方是当一个连接创建后，不需要对应一个线程，这个连接会被注册到多路复用器上面，所以所有的连接只需要一个线程就可以搞定，
 *  当这个线程中的多路复用器进行轮询的时候，发现连接上有请求的话，才开启一个线程进行处理，也就是一个请求一个线程模式。
 *
 * Reactor是什么？ 在Reactor模式中，事件分离者等待某个事件或者可应用或个操作的状态发生（比如文件描述符可读写，或者是socket可读写）,
 * 事件分离者就把这个事件传给事先注册的事件处理函数或者回调函数，由后者来做实际的读写操作。
 * 延伸：，Reactor是基于同步IO的，Proactor是基于异步IO的。
 *
 * 阻塞：在此种方式下，用户进程在发起一个IO操作以后，必须等待IO操作的完成，只有当真正完成了IO操作以后，用户进程才能运行。JAVA传统的IO模型属于此种方式！
 * 非阻塞：在此种方式下，用户进程发起一个IO操作以后边可返回做其它事情，但是用户进程需要时不时的询问IO操作是否就绪，这就要求用户进程不停的去询问，从而引入不必要的CPU资源浪费。其中目前JAVA的NIO就属于同步非阻塞IO。
 */
public class NIOServer {

    public static void main(String[] args) throws InterruptedException {
        ServerSocketChannel serverSocketChannel = null;
        Selector selector;
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(8888);

            serverSocketChannel.bind(address);
            //设置非阻塞
            serverSocketChannel.configureBlocking(false);
            //注册事件 -> OP_ACCEPT 接受到请求
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {

                //select() 方法将返回所发生的事件的数量。select()方法是阻塞的
                if (selector.select() > 0) {

                    Set<SelectionKey> selectedKeys = selector.selectedKeys(); // 获取发生的事件
                    Iterator<SelectionKey> it = selectedKeys.iterator(); // 依次进行处理

                    while (it.hasNext()) {
                        SelectionKey selectionKey = it.next();

                        if (selectionKey.isAcceptable()) { //收到连接

                            serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel sc = serverSocketChannel.accept(); // 建立连接
                            System.out.println(sc.isBlocking());
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println("connect build");
                        } else if (selectionKey.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                            ByteBuffer bf = ByteBuffer.allocate(1024);
                            int readByte = 0;
                            int ret;
                            while ((ret = socketChannel.read(bf)) > 0) {
                                readByte += ret;
                            }

                            System.out.println("共读取到字节：" + readByte);
                            bf.flip();

                            while (bf.hasRemaining()) {
                                byte b = bf.get();
                                System.out.print((char) b);
                            }
                            System.out.println();

                        }

                        //每次循环后需要移除处理完的事件，否则下次selectedKeys()还会再次获取到这事件
                        it.remove();
                    }

                }

                Thread.sleep(10);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocketChannel != null) {
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
