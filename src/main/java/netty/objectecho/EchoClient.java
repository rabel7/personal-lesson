package netty.objectecho;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.pojo.TimeClientHandler;
import netty.pojo.TimeDecoder;

/**
 * Created by daive on 2016/8/7.
 */
public class EchoClient {

    private final static String host = "127.0.0.1";

    private final static int port = 8080;

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup(1);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();

                            p.addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ObjectEchoClientHandler());
                        }
                    });

            // Start the connection attempt.

            boolean flag = true;
            while (flag) {

                Thread.sleep(1000);
                // Start the client.
                ChannelFuture f = b.connect(host, port).sync(); // (5)
                // Wait until the connection is closed.
                f.channel().closeFuture().sync();
            }

        } finally {
            group.shutdownGracefully();
        }
    }

}
