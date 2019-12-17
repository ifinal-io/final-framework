package org.finalframework.data.query.criterion.operation;


import org.finalframework.data.query.criterion.operator.CriterionOperator;
import org.finalframework.data.query.criterion.FunctionCriterion;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.LikeCriterionOperations;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-08 22:03:07
 * @since 1.0
 */
public class StringCriterionOperations extends BaseCriterionOperations<String> implements LikeCriterionOperations<String> {

    public static final StringCriterionOperations INSTANCE = new StringCriterionOperations();

    public StringCriterionOperations() {
        super(String.class, 16);
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

        register(startWith());
        register(notStartWith());
        register(endWith());
        register(notEndWith());
        register(contains());
        register(notContains());
        register(like());
        register(notLike());
    }

    @Override
    public StartWithCriterionOperation<String> startWith() {
        return new StartWithCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("%s LIKE '%s%%'", column, value);
            }
        };
    }

    @Override
    public NotStartWithCriterionOperation<String> notStartWith() {
        return new NotStartWithCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("%s NOT LIKE '%s%%'", column, value);
            }
        };
    }

    @Override
    public EndWithCriterionOperation<String> endWith() {
        return new EndWithCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("%s LIKE '%%%s'", column, value);
            }
        };
    }

    @Override
    public NotEndWithCriterionOperation<String> notEndWith() {
        return new NotEndWithCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("%s NOT LIKE '%%%s'", column, value);
            }
        };
    }

    @Override
    public ContainsCriterionOperation<String> contains() {
        return new ContainsCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("%s LIKE '%%%s%%'", column, value);
            }
        };
    }

    @Override
    public NotContainsCriterionOperation<String> notContains() {
        return new NotContainsCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("%s NOT LIKE '%%%s%%'", column, value);
            }
        };
    }

    @Override
    public LikeCriterionOperation<String> like() {
        return new LikeCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("%s LIKE '%s'", column, value);
            }
        };
    }

    @Override
    public NotLikeCriterionOperation<String> notLike() {
        return new NotLikeCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("%s NOT LIKE '%s'", column, value);
            }
        };
    }

    @Override
    public EqualCriterionOperation<String> eq() {
        return new EqualCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                return String.format("%s = '%s'", getPropertyColumn(property, functions), value);
            }
        };
    }

    @Override
    public NotEqualCriterionOperation<String> neq() {
        return new NotEqualCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                return String.format("%s != '%s'", getPropertyColumn(property, functions), value);
            }
        };
    }

    @Override
    public GreaterThanCriterionOperation<String> gt() {
        return new GreaterThanCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                return String.format("%s > '%s'", getPropertyColumn(property, functions), value);
            }
        };
    }

    @Override
    public GreaterThanEqualCriterionOperation<String> gte() {
        return new GreaterThanEqualCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                return String.format("%s >= '%s'", getPropertyColumn(property, functions), value);
            }
        };
    }

    @Override
    public LessThanCriterionOperation<String> lt() {
        return new LessThanCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                return String.format("%s < '%s'", getPropertyColumn(property, functions), value);
            }
        };
    }

    @Override
    public LessThanEqualCriterionOperation<String> lte() {
        return new LessThanEqualCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String value) {
                return String.format("%s <= '%s'", getPropertyColumn(property, functions), value);
            }
        };
    }

    @Override
    public InCriterionOperation<String> in() {
        return new InCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, Collection<String> value) {
                return String.format("%s IN %s", getPropertyColumn(property, functions),
                        value.stream().map(it -> String.format("'%s'", it)).collect(Collectors.joining(",", "(", ")")));
            }
        };
    }

    @Override
    public NotInCriterionOperation<String> nin() {
        return new NotInCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, Collection<String> value) {
                return String.format("%s NOT IN %s", getPropertyColumn(property, functions),
                        value.stream().map(it -> String.format("'%s'", it)).collect(Collectors.joining(",", "(", ")")));
            }
        };
    }

    @Override
    public NullCriterionOperation<String> isNull() {
        return new NullCriterionOperation<>();
    }

    @Override
    public NotNullCriterionOperation<String> nonNull() {
        return new NotNullCriterionOperation<>();
    }

    @Override
    public BetweenCriterionOperation<String> between() {
        return new BetweenCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String min, String max) {
                return String.format("%s BETWEEN '%s' AND '%s'", getPropertyColumn(property, functions), min, max);
            }
        };
    }

    @Override
    public NotBetweenCriterionOperation<String> notBetween() {
        return new NotBetweenCriterionOperation<String>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, String min, String max) {
                return String.format("%s NOT BETWEEN '%s' AND '%s'", getPropertyColumn(property, functions), min, max);
            }
        };
    }
}
