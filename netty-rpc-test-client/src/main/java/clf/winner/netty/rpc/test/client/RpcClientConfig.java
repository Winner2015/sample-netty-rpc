package clf.winner.netty.rpc.test.client;

import clf.winner.netty.rpc.core.client.RpcConsumerRegistry;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import clf.winner.netty.rpc.core.config.RpcConsumerConfig;

import java.util.List;

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
    public List<RpcConsumerConfig> rpcConsumerConfigList() {

        List<RpcConsumerConfig> configList = Lists.newArrayList();

        RpcConsumerConfig timeServiceConfig = new RpcConsumerConfig();
        timeServiceConfig.setServerHost("127.0.0.1");
        timeServiceConfig.setServerPort(13888);
        timeServiceConfig.setInterfaceClass("clf.winner.netty.rpc.test.server.api.TimeService");
        timeServiceConfig.setBeanId("timeService");

        configList.add(timeServiceConfig);

        return configList;
    }

    @Bean
    public static RpcConsumerRegistry rpcConsumerRegistry(List<RpcConsumerConfig> rpcConsumerConfigList) {
        RpcConsumerRegistry registry = new RpcConsumerRegistry();
        registry.setConfigList(rpcConsumerConfigList);

        return registry;
    }

}
