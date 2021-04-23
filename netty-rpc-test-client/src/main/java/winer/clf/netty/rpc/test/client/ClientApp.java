package winer.clf.netty.rpc.test.client;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import winer.clf.netty.rpc.common.param.RpcRequest;
import winer.clf.netty.rpc.common.param.RpcResponse;
import winer.clf.netty.rpc.core.client.RpcClient;

import java.util.UUID;

/**
 * @author chenlongfei
 */
@SpringBootApplication
public class ClientApp {

    public static void main(String [] args) throws InterruptedException {

        ConfigurableApplicationContext context = SpringApplication.run(ClientApp.class);

        AutowireCapableBeanFactory autowireCapableBeanFactory = context.getAutowireCapableBeanFactory();


        RpcClient rpcClient = (RpcClient)context.getBean("rpcClient");

        RpcResponse response = rpcClient.sendRequest(buildRequest());


    }

    private static RpcRequest buildRequest() {
        RpcRequest r = new RpcRequest();
        r.setId(UUID.randomUUID().toString());
        r.setClassName("winer.clf.netty.rpc.test.server.api.TimeService");
        r.setMethodName("getTime");

        return r;
    }
}
