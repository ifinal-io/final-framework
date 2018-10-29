package cn.com.likly.finalframework.coding.model;

import cn.com.likly.finalframework.coding.annotation.Template;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 13:39
 * @since 1.0
 */
@Template("entity/mapper.ftl")
public class Mapper {
    private String packageName;
    private String name;
    private String primaryKeyType;
    private String entityPackage;
    private String entityName;

    public Mapper(String packageName, String name, String primaryKeyType, String entityPackage, String entityName) {
        this.packageName = packageName;
        this.name = name;
        this.primaryKeyType = primaryKeyType;
        this.entityPackage = entityPackage;
        this.entityName = entityName;
    }

    public String getPackage() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getPrimaryKeyType() {
        return primaryKeyType;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public String getEntityName() {
        return entityName;
    }
}
