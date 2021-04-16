package winer.clf.netty.rpc.core.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenlongfei
 */
public class SimpleRpcServiceRegistry implements RpcServiceRegistry{

    private static final Map<String, Object> SERVICE_MAP = new HashMap<>();

    @Override
    public void registerServiceBean (String interfaceName, Object beanInstance) {
        SERVICE_MAP.put(interfaceName, beanInstance);
    }

    @Override
    public Object getServiceBean (String interfaceName) {
        return SERVICE_MAP.get(interfaceName);
    }
}
