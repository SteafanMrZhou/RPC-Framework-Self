package com.steafan.rpc.registry;

import com.steafan.rpc.model.ServiceMetadata;

/**
 * 服务注册发现接口
 *
 */
public interface IServiceRegistry {

    void registry(ServiceMetadata serviceMetadata);
}
