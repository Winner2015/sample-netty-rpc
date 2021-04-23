package winer.clf.netty.rpc.core.client;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import winer.clf.netty.rpc.core.config.RpcConsumerConfig;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author chenlongfei
 */
public class RpcConsumerRegistry implements BeanFactoryPostProcessor {

    List<RpcConsumerConfig> configList;

    @Override
    public void postProcessBeanFactory (ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        for (RpcConsumerConfig config : configList) {
            Object beanInstance = createProxy(config);
            configurableListableBeanFactory.registerSingleton(beanInstance.getClass().getSimpleName(), beanInstance);
        }
    }

    public <T> T createProxy(RpcConsumerConfig config) throws BeansException{
        try {
            Class<?> clazz = Class.forName(config.getInterfaceClass());

            InetSocketAddress remoteAddress = InetSocketAddress.createUnresolved(config.getServerHost(), config.getServerPort());
            RpcClient rpcClient = new RpcClient(remoteAddress);

            RpcInvoker invoker = new RpcInvoker(rpcClient);
            return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, invoker);
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(e.getMessage());
        }
    }
}
