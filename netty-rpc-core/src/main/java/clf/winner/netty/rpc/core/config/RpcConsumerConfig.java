package clf.winner.netty.rpc.core.config;

/**
 * @author chenlongfei
 */
public class RpcConsumerConfig {

    private String serverHost;
    private int serverPort;
    private String beanId;
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

    public String getBeanId () {
        return beanId;
    }

    public void setBeanId (String beanId) {
        this.beanId = beanId;
    }
}