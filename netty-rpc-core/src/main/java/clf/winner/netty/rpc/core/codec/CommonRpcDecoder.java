package clf.winner.netty.rpc.core.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import clf.winner.netty.rpc.core.serialization.Serialization;

import java.util.List;

/**
 * @author chenlongfei
 */
public class CommonRpcDecoder extends ByteToMessageDecoder {

    private Serialization serialization;
    private Class<?> clazz;

    public CommonRpcDecoder (Serialization serialization, Class<?>  clazz) {
        this.serialization = serialization;
        this.clazz = clazz;
    }

    @Override
        protected void decode (ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

            //报文有两部分组成：报文长度（int，4字节） + 报文内容

            if (in.readableBytes() < 4) { //如果不够4字节，说明已被拆包，还没有收到完整的报文，先跳过
                return;
            }

            in.markReaderIndex();
            int dataLength = in.readInt();
            if (in.readableBytes() < dataLength) {
                //比如，报文头标记：报文长度为100字节，但是当前buffer中只有80字节，说明剩余报文还没到达
                in.resetReaderIndex();
            }
            byte[] bytes = new byte[dataLength];
            in.readBytes(bytes);

            Object obj = serialization.deSerialize(bytes, clazz);

            out.add(obj);
        }
}
