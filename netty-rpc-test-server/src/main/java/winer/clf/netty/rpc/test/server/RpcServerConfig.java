package winer.clf.netty.rpc.test.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import winer.clf.netty.rpc.core.server.RpcServer;

/**
 * @author chenlongfei
 */
@Configuration
public class RpcServerConfig {

    @Value("${rpc.server.host}")
    private String host;

    @Value("${rpc.server.port}")
    private int port;

    @Bean
    public RpcServer rpcServer() {
        return new RpcServer(host, port);
    }
}
