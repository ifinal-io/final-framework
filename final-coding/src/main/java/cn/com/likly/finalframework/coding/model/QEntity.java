package cn.com.likly.finalframework.coding.model;

import cn.com.likly.finalframework.coding.annotation.Template;
import cn.com.likly.finalframework.coding.element.Entity;
import cn.com.likly.finalframework.coding.element.Property;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 13:24
 * @since 1.0
 */
@Template("entity/entity.ftl")
public class QEntity {
    private String packageName;
    private String name;
    private Entity<Property> entity;

    public QEntity(String packageName, String name, Entity<Property> entity) {
        this.packageName = packageName;
        this.name = name;
        this.entity = entity;
    }

    public String getPackage() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public Entity<Property> getEntity() {
        return entity;
    }
}
