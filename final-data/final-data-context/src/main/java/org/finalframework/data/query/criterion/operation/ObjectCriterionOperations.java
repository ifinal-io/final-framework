package org.finalframework.data.query.criterion.operation;


import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.SimpleCriterionOperations;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-08 22:15:33
 * @since 1.0
 */
public class ObjectCriterionOperations<T> extends BaseCriterionOperations<T> implements SimpleCriterionOperations<T> {

    public static final ObjectCriterionOperations<Object> INSTANCE = new ObjectCriterionOperations<>(Object.class);

    public ObjectCriterionOperations(Class<T> type) {
        super(type, 16);
        register(eq());
        register(neq());
        register(gt());
        register(gte());
        register(lt());
        register(lte());
        register(in());
        register(nin());
        register(between());
        register(notBetween());
        register(isNull());
        register(nonNull());
    }

    @Override
    public EqualCriterionOperation<T> eq() {
        return new EqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s = %s", getPropertyColumn(property, functions), value.toString());
            }
        };
    }

    @Override
    public NotEqualCriterionOperation<T> neq() {
        return new NotEqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s != %s", getPropertyColumn(property, functions), value.toString());
            }
        };
    }

    @Override
    public GreaterThanCriterionOperation<T> gt() {
        return new GreaterThanCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s > %s", getPropertyColumn(property, functions), value.toString());
            }
        };
    }

    @Override
    public GreaterThanEqualCriterionOperation<T> gte() {
        return new GreaterThanEqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s >= %s", getPropertyColumn(property, functions), value.toString());
            }
        };
    }

    @Override
    public LessThanCriterionOperation<T> lt() {
        return new LessThanCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s < %s", getPropertyColumn(property, functions), value.toString());
            }
        };
    }

    @Override
    public LessThanEqualCriterionOperation<T> lte() {
        return new LessThanEqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s <= %s", getPropertyColumn(property, functions), value.toString());
            }
        };
    }

    @Override
    public InCriterionOperation<T> in() {
        return new InCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, Collection<T> value) {
                return String.format("%s IN %s", getPropertyColumn(property, functions),
                        value.stream().map(Objects::toString).collect(Collectors.joining(",", "(", ")")));

            }
        };
    }

    @Override
    public NotInCriterionOperation<T> nin() {
        return new NotInCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, Collection<T> value) {
                return String.format("%s NOT IN %s", getPropertyColumn(property, functions),
                        value.stream().map(Objects::toString).collect(Collectors.joining(",", "(", ")")));
            }
        };
    }

    @Override
    public BetweenCriterionOperation<T> between() {
        return new BetweenCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T min, T max) {
                return String.format("%s BETWEEN %s AND %s", getPropertyColumn(property, functions), min.toString(), max.toString());
            }
        };
    }

    @Override
    public NotBetweenCriterionOperation<T> notBetween() {
        return new NotBetweenCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T min, T max) {
                return String.format("%s NOT BETWEEN %s AND %s", getPropertyColumn(property, functions), min.toString(), max.toString());
            }
        };
    }
}
