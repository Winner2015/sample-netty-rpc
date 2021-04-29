package clf.winner.netty.rpc.core.client;

import clf.winner.netty.rpc.core.server.RpcServer;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import clf.winner.netty.rpc.common.param.RpcRequest;
import clf.winner.netty.rpc.common.param.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenlongfei
 */
public class RpcClientHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientHandler.class);

    private RpcResponsePool rpcResponsePool;

    public RpcClientHandler(RpcResponsePool rpcResponsePool) {
        this.rpcResponsePool = rpcResponsePool;
    }

    @Override
    public void channelRead (ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RpcResponse) {
            logger.info("receive server response: " + JSON.toJSONString(msg));
            RpcResponse resp = (RpcResponse) msg;

            //接收到RPC结果，放到RpcResponsePool中，匹配发送时放入的请求ID
            rpcResponsePool.putResponse(resp);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void write (ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof RpcRequest) {
            logger.info("send request: " + JSON.toJSONString(msg));
            RpcRequest req = (RpcRequest) msg;

            //发送请求之前，先将请求ID，放入放到RpcResponsePool
            rpcResponsePool.putRequest(req.getId());
        }
        super.write(ctx, msg, promise);
    }
}
