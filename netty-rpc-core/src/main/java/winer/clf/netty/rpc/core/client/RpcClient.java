package winer.clf.netty.rpc.core.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import winer.clf.netty.rpc.common.param.RpcRequest;
import winer.clf.netty.rpc.common.param.RpcResponse;

/**
 * @author chenlongfei
 */
public class RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    private String host;
    private int port;

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Channel channel;
    private RpcResponsePool rpcResponsePool;

    public void connect () {

        EventLoopGroup loopGroup = new NioEventLoopGroup();
        rpcResponsePool = new RpcResponsePool();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup).
                channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new RpcClientChannelInitializer(rpcResponsePool));

        try {
            channel = bootstrap.connect(host, port).sync().channel();
            logger.info("connect rpc server success");
        } catch (Exception e) {
            e.printStackTrace();
            loopGroup.shutdownGracefully();
        }
    }

    public RpcResponse sendRequest (RpcRequest request) {
        channel.writeAndFlush(request);
        rpcResponsePool.putRequest(request.getId());
        return rpcResponsePool.takeResponse(request.getId());
    }

}
