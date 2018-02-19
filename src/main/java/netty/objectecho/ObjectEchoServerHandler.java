package netty.objectecho;

import collection.map.HashMapTest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.object.RpcRequest;
import netty.object.RpcResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class ObjectEchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        // Echo back the received object to the client.
        RpcRequest request = (RpcRequest) msg;
        System.out.println(Thread.currentThread().getName() + "receive rpc request :" + request.getMessageId());
        //todo class method by request param

        Thread.sleep(3000);

        RpcResponse response = new RpcResponse();
        response.setMessageId(request.getMessageId());

//        ctx.writeAndFlush(msg);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
