package com.ilikly.finalframework.spring.coding.processor;

import com.ilikly.finalframework.coding.annotation.Template;
import com.ilikly.finalframework.core.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-25 22:26:06
 * @since 1.0
 */
@Template("spring/spring.factories")
public class SpringFactories implements Serializable {

    private final List<String> autoConfigurations;
    private final List<String> applicationListeners;

    private SpringFactories(Builder builder) {
        this.autoConfigurations = Assert.isEmpty(builder.autoConfigurations) ? null : Collections.unmodifiableList(builder.autoConfigurations);
        this.applicationListeners = Assert.isEmpty(builder.applicationListeners) ? null : Collections.unmodifiableList(builder.applicationListeners);
    }

    public List<String> getAutoConfigurations() {
        return autoConfigurations;
    }

    public List<String> getApplicationListeners() {
        return applicationListeners;
    }

    @Override
    public String toString() {
        return "SpringFactories{" +
                "autoConfigurations=" + autoConfigurations +
                ", applicationListeners=" + applicationListeners +
                '}';
    }

    public static class Builder implements com.ilikly.finalframework.core.Builder<SpringFactories> {
        private final List<String> autoConfigurations = new ArrayList<>();
        private final List<String> applicationListeners = new ArrayList<>();


        public Builder addAutoConfiguration(String autoConfiguration) {
            if (!autoConfigurations.contains(autoConfiguration)) {
                this.autoConfigurations.add(autoConfiguration);
            }
            return this;
        }

        public Builder addApplicationListener(String applicationListener) {
            if (!applicationListeners.contains(applicationListener)) {
                this.applicationListeners.add(applicationListener);
            }
            return this;
        }

        @Override
        public SpringFactories build() {
            return new SpringFactories(this);
        }
    }
}
