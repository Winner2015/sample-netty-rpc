package winer.clf.netty.rpc.core.client;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import winer.clf.netty.rpc.common.param.RpcResponse;

/**
 * @author chenlongfei
 */
public class RpcClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientHandler.class);

    private RpcResponsePool rpcResponsePool;

    public RpcClientHandler(RpcResponsePool rpcResponsePool) {
        this.rpcResponsePool = rpcResponsePool;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("RpcClientHandler channelRead: " + JSON.toJSONString(msg));
        RpcResponse response = (RpcResponse)msg;
        rpcResponsePool.putResponse(response);
        ctx.fireChannelRead(msg);
    }

}
