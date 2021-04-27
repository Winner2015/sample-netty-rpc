package clf.winner.netty.rpc.core.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import clf.winner.netty.rpc.common.param.RpcRequest;
import clf.winner.netty.rpc.common.param.RpcResponse;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chenlongfei
 */
public class RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    private InetSocketAddress remoteAddress;

    public RpcClient(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    private AtomicBoolean connected = new AtomicBoolean(false);

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
            channel = bootstrap.connect(remoteAddress).sync().channel();
            logger.info("connect rpc server success");
        } catch (Exception e) {
            e.printStackTrace();
            loopGroup.shutdownGracefully();
        }
    }

    public RpcResponse sendRequest (RpcRequest request) throws InterruptedException {
        if (connected.compareAndSet(false, true)) {
            connect ();
        }

        channel.writeAndFlush(request).sync(); //发送完成之前，阻塞
        return rpcResponsePool.takeResponse(request.getId());
    }

}
