package winer.clf.netty.rpc.core.config;

public interface RpcServiceRegistry {

    void registerServiceBean (String interfaceName, Object beanInstance);

    Object getServiceBean (String interfaceName);
}
