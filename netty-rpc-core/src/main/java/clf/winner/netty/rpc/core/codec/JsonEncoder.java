package clf.winner.netty.rpc.core.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author chenlongfei
 */
public class JsonEncoder extends MessageToMessageEncoder {

    @Override
    protected void encode (ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        byte[] bytes = JSON.toJSONBytes(msg);

        //报文有两部分组成：报文长度 + 报文内容
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        out.add(byteBuf);
    }
}
