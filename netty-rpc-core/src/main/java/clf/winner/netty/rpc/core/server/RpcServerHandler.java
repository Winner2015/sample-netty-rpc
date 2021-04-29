package clf.winner.netty.rpc.core.server;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import clf.winner.netty.rpc.common.param.RpcRequest;
import clf.winner.netty.rpc.common.param.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author chenlongfei
 */
public class RpcServerHandler extends ChannelInboundHandlerAdapter{

    private static final Logger logger = LoggerFactory.getLogger(RpcServerHandler.class);

    private RpcProviderRegistry rpcServiceRegistry;

    public RpcServerHandler (RpcProviderRegistry rpcServiceRegistry) {
        this.rpcServiceRegistry = rpcServiceRegistry;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelActive: " + ctx.channel().remoteAddress());
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelInactive: " + ctx.channel().remoteAddress());
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        logger.info("channelRead: " + JSON.toJSONString(msg));

        if (msg instanceof  RpcRequest) {
            //收到了RPC请求
            RpcRequest request = (RpcRequest)msg;
            RpcResponse response = new RpcResponse();
            response.setRequestId(request.getId());

            try {
                Object data = handle(request); //处理数据
                response.setData(data);
            } catch (Exception e) {
                e.printStackTrace();
                response.setMsg(e.getMessage());
                response.setCode(-1);
            }

            //调用结果写入
            ctx.writeAndFlush(response);
        }

        ctx.fireChannelRead(msg);
    }

    private Object handle (RpcRequest request) throws Exception {

        //从注册表获取对应的服务
        String interfaceName = request.getClassName();
        Object serviceBean = rpcServiceRegistry.getServiceBean(interfaceName);
        if (serviceBean == null) {
            throw new Exception("no service provider: " + interfaceName);
        }

        Class<?> clazz = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        //利用反射，触发方法调用
        Method method = clazz.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelReadComplete");
        ctx.fireChannelReadComplete();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }


}
