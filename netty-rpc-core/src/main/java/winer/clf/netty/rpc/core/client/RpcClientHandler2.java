package winer.clf.netty.rpc.core.client;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import winer.clf.netty.rpc.common.param.RpcRequest;
import winer.clf.netty.rpc.common.param.RpcResponse;

/**
 * @author chenlongfei
 */
public class RpcClientHandler2 extends ChannelDuplexHandler {

    private RpcResponsePool rpcResponsePool;

    @Override
    public void channelRead (ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RpcResponse) {
            RpcResponse resp = (RpcResponse) msg;
            rpcResponsePool.putResponse(resp);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void write (ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof RpcRequest) {
            RpcRequest req = (RpcRequest) msg;
            rpcResponsePool.putRequest(req.getId());
        }
        super.write(ctx, msg, promise);
    }
}
