package winer.clf.netty.rpc.core.config;

import winer.clf.netty.rpc.core.client.RpcClient;
import winer.clf.netty.rpc.core.client.RpcInvoker;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * @author chenlongfei
 */
public class RpcConsumerConfig {

    private String serverHost;
    private int serverPort;
    private String interfaceClass;

    public String getServerHost () {
        return serverHost;
    }

    public void setServerHost (String serverHost) {
        this.serverHost = serverHost;
    }

    public int getServerPort () {
        return serverPort;
    }

    public void setServerPort (int serverPort) {
        this.serverPort = serverPort;
    }

    public String getInterfaceClass () {
        return interfaceClass;
    }

    public void setInterfaceClass (String interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}