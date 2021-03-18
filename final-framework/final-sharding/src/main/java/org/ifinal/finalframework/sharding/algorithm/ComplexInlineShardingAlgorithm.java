package org.ifinal.finalframework.sharding.algorithm;

import com.google.common.base.Preconditions;
import groovy.lang.Closure;
import groovy.util.Expando;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.sharding.algorithm.sharding.inline.InlineExpressionParser;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.sharding.spi.ShardingAlgorithm;
import org.ifinal.finalframework.sharding.annotation.ShardingStrategy;
import org.ifinal.auto.service.annotation.AutoService;
import org.ifinal.finalframework.util.collection.Maps;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(ShardingAlgorithm.class)
public class ComplexInlineShardingAlgorithm implements ComplexKeysShardingAlgorithm<Comparable<?>> {

    private static final String ALGORITHM_EXPRESSION_KEY = "algorithm-expression";

    private static final String SHARING_COLUMNS_KEY = "sharding-columns";

    private static final String ALLOW_RANGE_QUERY_KEY = "allow-range-query-with-inline-sharding";

    @Setter
    @Getter
    private Properties props;

    private boolean allowRangeQuery;

    private String[] shardingColumns;

    private String algorithmExpression;

    @Override
    public Collection<String> doSharding(final Collection<String> availableTargetNames,
        final ComplexKeysShardingValue<Comparable<?>> shardingValue) {

        if (Objects.nonNull(shardingValue.getColumnNameAndRangeValuesMap()) && !shardingValue
            .getColumnNameAndRangeValuesMap().isEmpty()) {
            if (isAllowRangeQuery()) {
                return availableTargetNames;
            }

            throw new UnsupportedOperationException(
                "Since the property of `" + ALLOW_RANGE_QUERY_KEY
                    + "` is false, inline sharding algorithm can not tackle with range query.");
        }

        Map<String, Collection<Comparable<?>>> columnNameAndShardingValuesMap = shardingValue
            .getColumnNameAndShardingValuesMap();

        if (shardingColumns.length > 0 && shardingColumns.length != columnNameAndShardingValuesMap.size()) {
            throw new IllegalArgumentException(
                "complex inline need " + shardingColumns.length + " sharing columns, but only found "
                    + columnNameAndShardingValuesMap.size());
        }

        Collection<Map<String, Comparable<?>>> combine = Maps.combine(columnNameAndShardingValuesMap);

        return combine.stream()
            .map(this::doSharding)
            .collect(Collectors.toList());

    }

    private String doSharding(final Map<String, Comparable<?>> shardingValues) {

        Closure<?> closure = createClosure();
        for (Map.Entry<String, Comparable<?>> entry : shardingValues.entrySet()) {
            closure.setProperty(entry.getKey(), entry.getValue());
        }
        return closure.call().toString();
    }

    @Override
    public void init() {
        String expression = props.getProperty(ALGORITHM_EXPRESSION_KEY);
        Preconditions.checkNotNull(expression, "Inline sharding algorithm expression cannot be null.");
        algorithmExpression = InlineExpressionParser.handlePlaceHolder(expression.trim());
        initShardingColumns(props.getProperty(SHARING_COLUMNS_KEY, ""));
        allowRangeQuery = Boolean
            .parseBoolean(props.getOrDefault(ALLOW_RANGE_QUERY_KEY, Boolean.FALSE.toString()).toString());
    }

    private void initShardingColumns(final String shardingColumns) {

        if (shardingColumns.length() == 0) {
            this.shardingColumns = new String[0];
            return;
        }
        this.shardingColumns = shardingColumns.split(",");
    }

    private boolean isAllowRangeQuery() {
        return allowRangeQuery;
    }

    private Closure<?> createClosure() {
        Closure<?> result = new InlineExpressionParser(algorithmExpression).evaluateClosure()
            .rehydrate(new Expando(), null, null);
        result.setResolveStrategy(Closure.DELEGATE_ONLY);
        return result;
    }

    @Override
    public String getType() {

        return ShardingStrategy.Algorithm.COMPLEX_INLINE;
    }

}
