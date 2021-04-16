package winer.clf.netty.rpc.core.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import winer.clf.netty.rpc.common.param.RpcRequest;
import winer.clf.netty.rpc.common.param.RpcResponse;
import winer.clf.netty.rpc.core.codec.CommonRpcDecoder;
import winer.clf.netty.rpc.core.codec.CommonRpcEncoder;
import winer.clf.netty.rpc.core.serialization.FastJsonSerialization;

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

        //InboundHandler，执行顺序为注册顺序
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,4));
        pipeline.addLast(new CommonRpcDecoder(new FastJsonSerialization(), RpcResponse.class));
        pipeline.addLast(new RpcClientHandler(rpcResponsePool));

        //OutboundHandler，执行顺序为注册顺序的逆序
        pipeline.addLast(new CommonRpcEncoder(new FastJsonSerialization(), RpcRequest.class));

    }


}