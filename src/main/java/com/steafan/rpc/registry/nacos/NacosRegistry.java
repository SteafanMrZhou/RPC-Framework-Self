package com.steafan.rpc.registry.nacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.steafan.rpc.model.ServiceMetadata;
import com.steafan.rpc.registry.IServiceRegistry;

import javax.imageio.spi.ServiceRegistry;

/**
 * @author Steafan(zyf)
 * @create 2022/11/29 14:27
 * @desc 服务注册发现Nacos实现
 */
public class NacosRegistry implements IServiceRegistry {

    @NacosInjected
    private ServiceRegistry serviceRegistry;


    @Override
    public void registry(ServiceMetadata serviceMetadata){
        serviceRegistry.registerServiceProvider(serviceMetadata);
    }
}
