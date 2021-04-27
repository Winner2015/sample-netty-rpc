package clf.winner.netty.rpc.core.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author chenlongfei
 */
public class JsonDecoder extends LengthFieldBasedFrameDecoder {

    //继承自变长解码器，将报文划分为报文头/报文体，根据报文头中的Length字段确定报文体的长度
    public JsonDecoder (int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        //最大报文长度、Length字段的起始位置、Length字段的长度
        super(Integer.MAX_VALUE, 0, 4);
    }

    //报文的粘包、拆包，由父类LengthFieldBasedFrameDecoder处理，这里只需要关注解码即可
    @Override
    protected Object decode (ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf byteBuf = (ByteBuf)super.decode(ctx, in);
        if (byteBuf == null) {
            return null;
        }

        int dataLength = byteBuf.readableBytes();
        byte[] bytes = new byte[dataLength];
        byteBuf.readBytes(bytes);

        Object obj = JSON.parse(bytes);
        return obj;
    }
}
