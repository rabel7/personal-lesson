package netty.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by daive on 2016/8/7.
 */
public class TimeDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        if (in.readableBytes() < 4) {
            return; // (3)
        }

        UnixTime unixTime = new UnixTime(in.readUnsignedInt());
        System.out.println("receive bytebuf from clinet and vonvert to pojo:" + unixTime);
        out.add(unixTime); // (4)
    }
}
