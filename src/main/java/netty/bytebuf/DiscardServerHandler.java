package netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端通道
 * Handles a server-side channel.
 * http://netty.io/wiki/user-guide-for-4.x.html
 * base discard protocal :https://tools.ietf.org/html/rfc863
 * <p>
 * (1) 为了重写事件所以继承ChannelInboundHandlerAdapter，因为这个例子简单所以没有用接口直接用继承
 * (2) This method is called with the received message, whenever new data is received from a client
 * (3) 协议规定的任何数据都无视，所以直接调用release(),这边要注意的是channelRead方法需要用try包装起来，并在finally中调用ReferenceCountUtil.release(msg)
 * (4) 很简单，就是netty处理出现异常就会被调用该方法。
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        }); // (4)
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
//        ByteBuf in = (ByteBuf) msg;
//        try {
//            while (in.isReadable()) { // (1) This inefficient loop can actually be simplified to: System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII))
////                System.out.print((char) in.readByte());
//                ctx.writeAndFlush(in);
////                System.out.flush();
//            }
//        } finally {
//            ReferenceCountUtil.release(msg); // (2) Alternatively, you could do in.release() here.
//        }
        ctx.writeAndFlush(msg); //echo 客户端发送啥返回啥给他

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}