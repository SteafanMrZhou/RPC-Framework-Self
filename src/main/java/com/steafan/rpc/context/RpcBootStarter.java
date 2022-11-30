package com.steafan.rpc.context;

import com.steafan.rpc.annotation.RpcProvider;
import com.steafan.rpc.common.RpcProperties;
import com.steafan.rpc.model.ServiceMetadata;
import com.steafan.rpc.registry.IServiceRegistry;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.rmi.registry.Registry;
import java.util.Map;
import java.util.Objects;

/**
 * @author Steafan(zyf)
 * @create 2022/11/29 14:35
 * @desc
 */
@Log4j2
public class RpcBootStarter implements ApplicationListener<ContextRefreshedEvent> {

    private IServiceRegistry serviceRegistry;

    private RpcProperties rpcProperties;

    public RpcBootStarter(IServiceRegistry serviceRegistry, RpcProperties rpcProperties){
        this.serviceRegistry = serviceRegistry;
        this.rpcProperties = rpcProperties;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (Objects.isNull(contextRefreshedEvent.getApplicationContext().getParent())) {
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            // 提供服务
            registerService(applicationContext);
            // 订阅服务
//            subscribeService(applicationContext);
        }
    }

    private void registerService(ApplicationContext context) {
        Map<String, Object> beanMap = context.getBeansWithAnnotation(RpcProvider.class);
        if (beanMap.size() > 0) {
            for (Object obj : beanMap.values()) {
                Class<?> clazz = obj.getClass();
                try {
                    RpcProvider rpcProvider = clazz.getAnnotation(RpcProvider.class);
                    if (StringUtils.isEmpty(rpcProvider.name())) {
                        throw new RuntimeException("rpc service class:" + clazz.getName() + "need service name config");
                    }
                    ServiceMetadata serviceMetadata = new ServiceMetadata();
                    serviceMetadata.setName(rpcProvider.name());
                    serviceMetadata.setVersion(rpcProvider.version());
                    serviceMetadata.setClazz(clazz);
                    // 调用服务注册中心注册服务
                    serviceRegistry.registry(serviceMetadata);
                } catch (Exception e) {
                    log.error("rpc service class: {}, register error", clazz.getName(), e);
                    throw new RuntimeException("rpc service class:" + clazz.getName() + "register error");
                }
            }
            // TODO: 2022/11/29 启动netty服务端，监听客户端请求
        }
    }
}
