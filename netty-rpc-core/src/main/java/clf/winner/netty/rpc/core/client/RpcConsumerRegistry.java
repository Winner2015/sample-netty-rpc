package clf.winner.netty.rpc.core.client;

import clf.winner.netty.rpc.core.config.RpcConsumerConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author chenlongfei
 */
public class RpcConsumerRegistry implements BeanFactoryPostProcessor {//BeanDefinitionRegistryPostProcessor

    private List<RpcConsumerConfig> configList; //RPC服务的配置信息

    @Override
    public void postProcessBeanFactory (ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        if (CollectionUtils.isEmpty(configList)) {
            return;
        }

        //将代理类注册为Spring的bean
        configList.forEach(config -> {
            Object beanInstance = createProxy(config);
            configurableListableBeanFactory.registerSingleton(config.getBeanId(), beanInstance);
        });
    }

    /**
     * 创建远程服务的本地代理
    */
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

    public List<RpcConsumerConfig> getConfigList () {
        return configList;
    }

    public void setConfigList (List<RpcConsumerConfig> configList) {
        this.configList = configList;
    }
}
