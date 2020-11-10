package org.finalframework.auto.mybatis.processor;


import org.finalframework.auto.coding.annotation.Template;
import org.finalframework.auto.coding.file.JavaSource;
import org.finalframework.auto.data.Entity;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-26 13:12:09
 * @since 1.0
 */
@Template("mapper/mapper.jvm")
public class Mapper implements JavaSource, Serializable {

    private static final long serialVersionUID = 6273326791444775523L;
    private final String packageName;
    private final String simpleName;
    private final String name;
    private final Boolean inner;
    private final Entity entity;

    private Mapper(Builder builder) {
        this.packageName = builder.packageName;
        this.simpleName = builder.simpleName;
        this.name = this.packageName + "." + this.simpleName;
        this.inner = builder.inner;
        this.entity = builder.entity;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    public Boolean getInner() {
        return inner;
    }

    public Entity getEntity() {
        return entity;
    }

    public static class Builder implements org.finalframework.util.Builder<Mapper> {
        private String packageName;
        private String simpleName;
        private Boolean inner;
        private Entity entity;

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder inner(boolean inner) {
            this.inner = inner;
            return this;
        }

        public Builder simpleName(String simpleName) {
            this.simpleName = simpleName;
            return this;
        }

        public Builder entity(Entity entity) {
            this.entity = entity;
            return this;
        }

        @Override
        public Mapper build() {
            return new Mapper(this);
        }
    }
}
