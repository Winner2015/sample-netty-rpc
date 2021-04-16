package winer.clf.netty.rpc.test.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import winer.clf.netty.rpc.core.client.RpcClient;

/**
 * @author chenlongfei
 */
@Configuration
public class RpcClientConfig {

    @Value("${rpc.server.host}")
    private String host;

    @Value("${rpc.server.port}")
    private int port;

    @Bean
    public RpcClient rpcClient() {
        RpcClient client = new RpcClient(host, port);
        client.connect();

        return client;
    }
}
