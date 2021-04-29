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

    private InetSocketAddress remoteAddress; //服务器地址

    public RpcClient(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    private AtomicBoolean connected = new AtomicBoolean(false);

    private Channel channel; //连接服务器成功后，会获取一个Channel
    private RpcResponsePool rpcResponsePool; //存储RPC的调用结果

    public void connect () {

        EventLoopGroup loopGroup = new NioEventLoopGroup();
        rpcResponsePool = new RpcResponsePool();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup).
                channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new RpcClientChannelInitializer(rpcResponsePool)); //注册编解码器、客户端处理器等

        try {
            channel = bootstrap.connect(remoteAddress).sync().channel();
            logger.info("connect rpc server success");
        } catch (Exception e) {
            e.printStackTrace();
            loopGroup.shutdownGracefully();
        }
    }

    public RpcResponse sendRequest (RpcRequest request) throws InterruptedException {
        if (connected.compareAndSet(false, true)) { //防止重复连接
            connect ();
        }

        channel.writeAndFlush(request).sync(); //发送完成之前，会阻塞住

        return rpcResponsePool.takeResponse(request.getId());
    }

}
