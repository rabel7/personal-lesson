package socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 主线程接收连接，一旦连接成功交给线程处理
 */
public class SelectorServerTest {

    public static void main(String args[]) throws IOException, InterruptedException {
        Selector selector = Selector.open(); // 创建Selector

        // 创建一个用于建立连接的ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(
                InetAddress.getLocalHost(), 8888);
        serverSocketChannel.socket().bind(address);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 注册Accept事件

        while (true) {
            if (selector.select() > 0) { // 如果注册的事件发生
                Set<SelectionKey> selectedKeys = selector.selectedKeys(); // 获取发生的事件
                Iterator<SelectionKey> it = selectedKeys.iterator(); // 依次进行处理
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    if (selectionKey.isAcceptable()) { // 如果是Accept事件
                        // 获取注册的ServerSocketChannel
                        serverSocketChannel = ((ServerSocketChannel) selectionKey.channel());
                        SocketChannel socketChannel = serverSocketChannel.accept(); // 建立连接
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ); // 注册该连接的Read事件
                        System.out.println("Connected: " + socketChannel.socket().getRemoteSocketAddress());
                    } else if (selectionKey.isReadable()) { // 如果是Read事件
                        // 获取注册的SocketChannel
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        while (socketChannel.read(buffer) > 0) { // 读取接收到的数据
                            buffer.flip();
                            byte[] dst = new byte[buffer.limit()];
                            buffer.get(dst);
                            System.out.println(new String(dst));
                        }
                    }
                    it.remove(); // 需要将处理过的事件移除
                }
            }
            Thread.sleep(100);
        }
    }

}