package winer.clf.netty.rpc.core.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import winer.clf.netty.rpc.common.param.RpcRequest;
import winer.clf.netty.rpc.common.param.RpcResponse;
import winer.clf.netty.rpc.core.codec.CommonRpcDecoder;
import winer.clf.netty.rpc.core.codec.CommonRpcEncoder;
import winer.clf.netty.rpc.core.config.RpcServiceRegistry;
import winer.clf.netty.rpc.core.serialization.FastJsonSerialization;

/**
 * @author chenlongfei
 */
public class RpcServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private RpcServiceRegistry rpcServiceRegistry;

    public RpcServerChannelInitializer (RpcServiceRegistry rpcServiceRegistry) {
        this.rpcServiceRegistry = rpcServiceRegistry;
    }

    @Override
    protected void initChannel (SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        //InboundHandler，执行顺序为注册顺序
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,4));
        pipeline.addLast(new CommonRpcDecoder(new FastJsonSerialization(), RpcRequest.class));
        pipeline.addLast(new RpcServerHandler(rpcServiceRegistry));

        //OutboundHandler，执行顺序为注册顺序的逆序
        pipeline.addLast(new CommonRpcEncoder(new FastJsonSerialization(), RpcResponse.class));

    }
}
