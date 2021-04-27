package clf.winner.netty.rpc.core.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import clf.winner.netty.rpc.core.serialization.Serialization;

/**
 * @author chenlongfei
 */
public class CommonRpcEncoder extends MessageToByteEncoder {

    private Serialization serialization;
    private Class<?> clazz;

    public CommonRpcEncoder (Serialization serialization, Class<?>  clazz) {
        this.serialization = serialization;
        this.clazz = clazz;
    }

    @Override
    protected void encode (ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (msg != null) {
            byte[] bytes = serialization.serialize(msg);

            //报文有两部分组成：报文长度 + 报文内容
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}
