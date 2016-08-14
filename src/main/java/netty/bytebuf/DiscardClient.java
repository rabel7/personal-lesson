package netty.bytebuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by daive on 2016/8/7.
 */
public class DiscardClient {

    private final static String host = "127.0.0.1";

    private final static int port = 8080;

    public static void main(String[] args) throws Exception {

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup)
                    .channel(NioSocketChannel.class) // (2)
                    .option(ChannelOption.SO_KEEPALIVE, true) // (4)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardDecoder(), new DiscardClientHandler());
                        }
                    });

            boolean flag = true;
            int num = 0;
            while (flag) {
                if(num++ > 10){
                    flag = false;
                }
                // Start the client.
                ChannelFuture f = b.connect(host, port).sync(); // (5)
                    // Wait until the connection is closed.
                f.channel().closeFuture().sync();
            }

        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
