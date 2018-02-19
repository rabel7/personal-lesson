package netty.objectecho;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.pojo.TimeEncoder;
import netty.pojo.TimeServerHandler;

/**
 *
 * 此netty例子与DiscardServer的不同在于，DiscardServer传输的是ByteBuf,而本例子直接引入对象传输,加入两个概念,decoder和encoder
 * 客户端加入Decoder,目的：将接收到的ByteBuf转换成pojo然后交给handler进行后续处理
 * 服务端加入Encoder，目的：将pojo转换成ByteBuf然后交给handler进行传输
 * 转换的过程在pojo中进行体现,比如在pojo构造函数中传入ByteBuf进行初始化
 *
 * 调用过程说明：
 * 1.client 客户端请求服务端
 * 2.server 服务端接收到客户端请求将pojo encoder 成ByteBuf格式
 * 3.服务端调用完encoder后由后续的handler连接建立方法将转换后的ByteBuf返回给客户端
 * 3.decoder 客户端接收到服务端的请求,通过注册的decoder将ByteBuf格式的数据转换成pojo
 * 4.客户端再decoder后的pojo交给后续handler进行处理，此时的方法是Object,进行强制转换后则是pojo
 *
 * 其他例子：https://github.com/netty/netty/tree/4.0/example/src/main/java/io/netty/example
 */
public class EchoServer {

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
       /* bossGroup用来接收连接
        workerGroup用来将bossGroup接收到的连接注册到woker中去，要使用多少个线程，取决于NioEventLoopGroup构造函数*/
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // (1) NioEventLoopGroup is a multithreaded event loop that handles I/O operation
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ObjectEchoServerHandler());
                        }
                    });

            // Bind and start to accept incoming connections.
            b.bind(this.port).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new EchoServer(port).start();
    }
}