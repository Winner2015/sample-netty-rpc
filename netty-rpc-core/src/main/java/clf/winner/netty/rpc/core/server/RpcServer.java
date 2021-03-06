package clf.winner.netty.rpc.core.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import clf.winner.netty.rpc.core.config.RpcService;

import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * @author chenlongfei
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    //注册表，用于存储暴露出去的RPC服务
    private RpcProviderRegistry rpcServiceRegistry = new RpcProviderRegistry();

    EventLoopGroup bossGroup = null;
    EventLoopGroup workGroup = null;

    private String host;
    private int port;

    public RpcServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void afterPropertiesSet () throws Exception {
        start();
    }

    @Override
    public void setApplicationContext (ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        beanMap.forEach((beanName, bean) -> {
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            String interfaceName = interfaces[0].getName();
            logger.info("find rpc service: " + interfaceName);

            //被@RpcService标注的bean，会被自动注册为RPC服务
            rpcServiceRegistry.registerServiceBean(interfaceName, bean);
        });
    }

    /**
     * 启动服务器
    */
    private void start () {

        //Netty 是基于 Reacotr 模型的，所以需要初始化两组线程 boss 和 worker
        // boss 负责分发请求，worker 负责执行相应的 handler
        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup(4);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new RpcServerChannelInitializer(rpcServiceRegistry));//注册编解码器、服务处理器等

        try {
            ChannelFuture cf = bootstrap.bind(host, port).sync();
            logger.info("rpc server started");
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    @PreDestroy
    public void destory() throws InterruptedException {
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
        logger.info("rpc server shutdown");
    }

}