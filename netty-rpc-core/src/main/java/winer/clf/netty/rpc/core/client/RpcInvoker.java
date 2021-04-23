package winer.clf.netty.rpc.core.client;

import winer.clf.netty.rpc.common.param.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author chenlongfei
 */
public class RpcInvoker implements InvocationHandler {

    private RpcClient rpcClient;

    public RpcInvoker (RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    @Override
    public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest r = new RpcRequest();
        r.setId(UUID.randomUUID().toString());
        r.setClassName(method.getDeclaringClass().getName());
        r.setMethodName(method.getName());
        r.setParameterTypes(method.getParameterTypes());
        r.setParameters(args);

        return rpcClient.sendRequest(r);
    }

}
