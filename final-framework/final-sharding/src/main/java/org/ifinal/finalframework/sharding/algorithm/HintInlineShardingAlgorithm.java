package org.ifinal.finalframework.sharding.algorithm;

import com.google.common.base.Preconditions;
import groovy.lang.Closure;
import groovy.util.Expando;
import org.apache.shardingsphere.sharding.algorithm.sharding.inline.InlineExpressionParser;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;
import org.apache.shardingsphere.sharding.spi.ShardingAlgorithm;
import org.ifinal.finalframework.auto.service.annotation.AutoService;

import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(ShardingAlgorithm.class)
public class HintInlineShardingAlgorithm implements HintShardingAlgorithm<Comparable<?>> {

    private static final String ALGORITHM_EXPRESSION_KEY = "algorithm-expression";
    private static final String DEFAULT_ALGORITHM_EXPRESSION = "${value}";
    private static final String HINT_INLINE_VALUE_PROPERTY_NAME = "value";
    private final Properties properties = new Properties();
    private String algorithmExpression;

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<Comparable<?>> shardingValue) {

        if (shardingValue.getValues().isEmpty()) {
            return availableTargetNames;
        }

        return shardingValue.getValues().stream()
                .map(this::doSharding)
                .collect(Collectors.toList());

    }

    private String doSharding(Comparable<?> shardingValue) {
        Closure<?> closure = createClosure();
        closure.setProperty(HINT_INLINE_VALUE_PROPERTY_NAME, shardingValue);
        return closure.call().toString();
    }


    @Override
    public void init() {
        String expression = properties.getProperty(ALGORITHM_EXPRESSION_KEY, DEFAULT_ALGORITHM_EXPRESSION);
        Preconditions.checkNotNull(expression, "Inline sharding algorithm expression cannot be null.");
        algorithmExpression = InlineExpressionParser.handlePlaceHolder(expression.trim());
    }

    private Closure<?> createClosure() {
        Closure<?> result = new InlineExpressionParser(algorithmExpression).evaluateClosure().rehydrate(new Expando(), null, null);
        result.setResolveStrategy(Closure.DELEGATE_ONLY);
        return result;
    }

    @Override
    public String getType() {
        return "HINT_INLINE";
    }

    @Override
    public Properties getProps() {
        return properties;
    }

    @Override
    public void setProps(Properties props) {
        properties.clear();
        properties.putAll(props);
    }

}
