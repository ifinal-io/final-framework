package cn.com.likly.finalframework.mybatis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 00:45:11
 * @since 1.0
 */
public class ResultMap implements Serializable {
    private final String id;
    private final Class type;
    private final Boolean autoMapping;
    private final Result idResult;
    private final List<Result> results;
    private final List<Association> associations;

    private ResultMap(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.autoMapping = builder.autoMapping;
        this.idResult = builder.idResult;
        this.results = builder.results.isEmpty() ? null : Collections.unmodifiableList(builder.results);
        this.associations = builder.associations.isEmpty() ? null : Collections.unmodifiableList(builder.associations);
    }

    public static Builder builder(String id, Class type) {
        return new Builder(id, type);
    }

    public String getId() {
        return id;
    }

    public Class getType() {
        return type;
    }

    public Boolean getAutoMapping() {
        return autoMapping;
    }

    public Result getIdResult() {
        return idResult;
    }

    public List<Result> getResults() {
        return results;
    }

    public static class Builder implements cn.com.likly.finalframework.core.Builder<ResultMap> {
        private final String id;
        private final Class type;
        private final List<Result> results = new ArrayList<>();
        private final List<Association> associations = new ArrayList<>();
        private Boolean autoMapping;
        private Result idResult;

        private Builder(String id, Class type) {
            this.id = id;
            this.type = type;
        }

        public Builder addResult(Result result) {
            this.results.add(result);
            if (result.isIdResult()) {
                idResult = result;
            }
            return this;
        }

        public Builder addAssociation(Association association) {
            this.associations.add(association);
            return this;
        }


        public Builder autoMapping(boolean autoMapping) {
            this.autoMapping = autoMapping;
            return this;
        }

        @Override
        public ResultMap build() {
            return new ResultMap(this);
        }
    }

}
