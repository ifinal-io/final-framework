package org.finalframework.coding.query;


import org.finalframework.coding.annotation.Template;
import org.finalframework.coding.file.JavaSource;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-27 22:07:04
 * @since 1.0
 */
@Template("query/criteria.jvm")
public class Criteria implements JavaSource {
    private final String packageName;
    private final String simpleName;
    private final String name;
    private final QEntity entity;

    private Criteria(Builder builder) {
        this.packageName = builder.packageName;
        this.simpleName = builder.simpleName;
        this.name = builder.packageName + "." + builder.simpleName;
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
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public String getName() {
        return name;
    }

    public QEntity getEntity() {
        return entity;
    }

    public static class Builder implements org.finalframework.core.Builder<Criteria> {
        private String packageName;
        private String simpleName;
        private QEntity entity;

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder simpleName(String simpleName) {
            this.simpleName = simpleName;
            return this;
        }

        public Builder entity(QEntity entity) {
            this.entity = entity;
            return this;
        }


        @Override
        public Criteria build() {
            return new Criteria(this);
        }
    }
}

