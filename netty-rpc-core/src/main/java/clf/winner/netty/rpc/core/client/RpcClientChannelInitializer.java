package clf.winner.netty.rpc.core.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import clf.winner.netty.rpc.common.param.RpcRequest;
import clf.winner.netty.rpc.common.param.RpcResponse;
import clf.winner.netty.rpc.core.codec.CommonRpcDecoder;
import clf.winner.netty.rpc.core.codec.CommonRpcEncoder;
import clf.winner.netty.rpc.core.serialization.FastJsonSerialization;

/**
 * @author chenlongfei
 */
public class RpcClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private RpcResponsePool rpcResponsePool;

    public RpcClientChannelInitializer(RpcResponsePool rpcResponsePool) {
        this.rpcResponsePool = rpcResponsePool;
    }

    @Override
    protected void initChannel (SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,4));
        pipeline.addLast(new CommonRpcEncoder(new FastJsonSerialization(), RpcRequest.class));
        pipeline.addLast(new CommonRpcDecoder(new FastJsonSerialization(), RpcResponse.class));

        pipeline.addLast(new RpcClientHandler(rpcResponsePool)); //即使InboundHandler，又是OutboundHandler，必须放在最后

    }

}
