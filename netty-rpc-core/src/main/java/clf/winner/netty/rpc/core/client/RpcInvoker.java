package clf.winner.netty.rpc.core.client;

import clf.winner.netty.rpc.common.param.RpcRequest;
import clf.winner.netty.rpc.common.param.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author chenlongfei
 */
public class RpcInvoker implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcInvoker.class);

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

        //通过Netty的发送请求，并接受调用结果
        RpcResponse response = rpcClient.sendRequest(r);
        if (response.getCode() == -1) {
            throw new RuntimeException("invoke rpc service failed: " + response.getMsg());
        }

        return response.getData();
    }

}
