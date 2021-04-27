package clf.winner.netty.rpc.core.server;

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
public class RpcServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private RpcProviderRegistry rpcServiceRegistry;

    public RpcServerChannelInitializer (RpcProviderRegistry rpcServiceRegistry) {
        this.rpcServiceRegistry = rpcServiceRegistry;
    }

    @Override
    protected void initChannel (SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        //InboundHandler，执行顺序为注册顺序
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,4));
        pipeline.addLast(new CommonRpcDecoder(new FastJsonSerialization(), RpcRequest.class));

        //OutboundHandler，执行顺序为注册顺序的逆序
        pipeline.addLast(new CommonRpcEncoder(new FastJsonSerialization(), RpcResponse.class));
        pipeline.addLast(new RpcServerHandler(rpcServiceRegistry));



    }
}
