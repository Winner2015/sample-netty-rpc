package clf.winner.netty.rpc.core.client;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import clf.winner.netty.rpc.common.param.RpcRequest;
import clf.winner.netty.rpc.common.param.RpcResponse;

/**
 * @author chenlongfei
 */
public class RpcClientHandler extends ChannelDuplexHandler {

    private RpcResponsePool rpcResponsePool;

    public RpcClientHandler(RpcResponsePool rpcResponsePool) {
        this.rpcResponsePool = rpcResponsePool;
    }

    @Override
    public void channelRead (ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RpcResponse) {
            System.out.println("receive server response: " + JSON.toJSONString(msg));
            RpcResponse resp = (RpcResponse) msg;
            rpcResponsePool.putResponse(resp);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void write (ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof RpcRequest) {
            System.out.println("send request: " + JSON.toJSONString(msg));
            RpcRequest req = (RpcRequest) msg;
            rpcResponsePool.putRequest(req.getId());
        }
        super.write(ctx, msg, promise);
    }
}
