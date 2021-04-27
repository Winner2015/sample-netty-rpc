package clf.winner.netty.rpc.core.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenlongfei
 */
public class RpcProviderRegistry {

    private static final Map<String, Object> SERVICE_MAP = new HashMap<>();

    public void registerServiceBean (String interfaceName, Object beanInstance) {
        SERVICE_MAP.put(interfaceName, beanInstance);
    }

    public Object getServiceBean (String interfaceName) {
        return SERVICE_MAP.get(interfaceName);
    }
}
