package org.finalframework.coding.query;


import org.finalframework.coding.annotation.Template;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-21 17:56:47
 * @since 1.0
 */
@Template("query/query.vm")
public class Query {
    private final String packageName;
    private final String simpleName;
    private final String name;
    private final QEntity entity;

    private Query(Builder builder) {
        this.packageName = builder.packageName;
        this.simpleName = builder.simpleName;
        this.name = builder.packageName + "." + builder.simpleName;
        this.entity = builder.entity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPackageName() {
        return packageName;
    }


    public String getSimpleName() {
        return simpleName;
    }


    public String getName() {
        return name;
    }

    public QEntity getEntity() {
        return entity;
    }

    public static class Builder implements org.finalframework.core.Builder<Query> {
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
        public Query build() {
            return new Query(this);
        }
    }
}

