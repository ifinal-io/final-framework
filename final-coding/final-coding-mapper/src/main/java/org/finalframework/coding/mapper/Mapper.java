package org.finalframework.coding.mapper;


import org.finalframework.coding.annotation.Template;
import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-26 13:12:09
 * @since 1.0
 */
@Template("mapper/mapper.jvm")
public class Mapper implements Serializable {

    private static final long serialVersionUID = 6273326791444775523L;
    private final String packageName;
    private final String name;
    private final Boolean inner;
    private final Entity<Property> entity;

    private Mapper(Builder builder) {
        this.packageName = builder.packageName;
        this.name = builder.name;
        this.inner = builder.inner;
        this.entity = builder.entity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public Boolean getInner() {
        return inner;
    }

    public Entity<Property> getEntity() {
        return entity;
    }

    public static class Builder implements org.finalframework.core.Builder<Mapper> {
        private String packageName;
        private String name;
        private Boolean inner;
        private Entity<Property> entity;

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder inner(boolean inner) {
            this.inner = inner;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
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
