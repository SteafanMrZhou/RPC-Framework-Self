package com.steafan.rpc.model;

/**
 * @author Steafan(zyf)
 * @create 2022/11/29 14:47
 * @desc 服务元数据
 */
public class ServiceMetadata {
    private String name;
    private String version;
    private Class<?> clazz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
