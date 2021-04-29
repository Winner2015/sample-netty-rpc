package clf.winner.netty.rpc.test.client;

import clf.winner.netty.rpc.core.client.RpcConsumerRegistry;
import clf.winner.netty.rpc.core.config.RpcConsumerConfig;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author chenlongfei
 */
@Configuration
public class RpcClientConfig {

    @Bean
    public List<RpcConsumerConfig> rpcConsumerConfigList() {

        List<RpcConsumerConfig> configList = Lists.newArrayList();

        //服务提供者的配置信息
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
