package org.finalframework.data.query.criterion.operation;


import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-08 22:13:20
 * @since 1.0
 */
public abstract class AbsDateCriterionOperations<T> extends BaseCriterionOperations<T> implements org.finalframework.data.query.criterion.DateCriterionOperations<T> {

    public AbsDateCriterionOperations(Class<T> type) {
        super(type, 32);
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

        register(before());
        register(after());
        register(dateEqual());
        register(notDateEqual());
        register(dateBefore());
        register(dateAfter());
        register(dateBetween());
        register(notDateBetween());
    }

    @Override
    public BeforeCriterionOperation<T> before() {
        return new BeforeCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s < '%s'", getPropertyColumn(property, functions), dateTime(value));
            }
        };
    }

    @Override
    public AfterCriterionOperation<T> after() {
        return new AfterCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s > '%s'", getPropertyColumn(property, functions), dateTime(value));
            }
        };
    }

    @Override
    public DateEqualCriterionOperation<T> dateEqual() {
        return new DateEqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("DATE(%s) = '%s'", column, date(value));
            }

        };
    }

    @Override
    public NotDateEqualCriterionOperation<T> notDateEqual() {
        return new NotDateEqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("DATE(%s) != '%s'", column, date(value));
            }
        };
    }

    @Override
    public DateBeforeCriterionOperation<T> dateBefore() {
        return new DateBeforeCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("DATE(%s) < '%s'", column, dateTime(value));
            }
        };
    }

    @Override
    public DateAfterCriterionOperation<T> dateAfter() {
        return new DateAfterCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                final String column = getPropertyColumn(property, functions);
                return String.format("DATE(%s) > '%s'", column, dateTime(value));
            }
        };
    }

    @Override
    public DateBetweenCriterionOperation<T> dateBetween() {
        return new DateBetweenCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T min, T max) {
                final String column = getPropertyColumn(property, functions);
                return String.format("DATE(%s) BETWEEN '%s' AND '%s'", column, dateTime(min), dateTime(max));
            }
        };
    }

    @Override
    public NotDateBetweenCriterionOperation<T> notDateBetween() {
        return new NotDateBetweenCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T min, T max) {
                final String column = getPropertyColumn(property, functions);
                return String.format("DATE(%s) NOT BETWEEN '%s' AND '%s'", column, dateTime(min), dateTime(max));
            }
        };
    }

    @Override
    public EqualCriterionOperation<T> eq() {
        return new EqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s = '%s'", getPropertyColumn(property, functions), dateTime(value));
            }
        };
    }

    @Override
    public NotEqualCriterionOperation<T> neq() {
        return new NotEqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s != '%s'", getPropertyColumn(property, functions), dateTime(value));
            }
        };
    }

    @Override
    public GreaterThanCriterionOperation<T> gt() {
        return new GreaterThanCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s > '%s'", getPropertyColumn(property, functions), dateTime(value));
            }
        };
    }

    @Override
    public GreaterThanEqualCriterionOperation<T> gte() {
        return new GreaterThanEqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s >= '%s'", getPropertyColumn(property, functions), dateTime(value));
            }
        };
    }

    @Override
    public LessThanCriterionOperation<T> lt() {
        return new LessThanCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s < '%s'", getPropertyColumn(property, functions), dateTime(value));
            }
        };
    }

    @Override
    public LessThanEqualCriterionOperation<T> lte() {
        return new LessThanEqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                return String.format("%s <= '%s'", getPropertyColumn(property, functions), dateTime(value));
            }
        };
    }

    @Override
    public InCriterionOperation<T> in() {
        return new InCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, Collection<T> value) {
                return String.format("%s IN %s", getPropertyColumn(property, functions),
                        value.stream().map(it -> String.format("'%s'", dateTime(it))).collect(Collectors.joining(",", "(", ")")));
            }
        };
    }

    @Override
    public NotInCriterionOperation<T> nin() {
        return new NotInCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, Collection<T> value) {
                return String.format("%s NOT IN %s", getPropertyColumn(property, functions),
                        value.stream().map(it -> String.format("'%s'", dateTime(it))).collect(Collectors.joining(",", "(", ")")));
            }
        };
    }

    @Override
    public BetweenCriterionOperation<T> between() {
        return new BetweenCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T min, T max) {
                return String.format("%s BETWEEN '%s' AND '%s'", getPropertyColumn(property, functions), dateTime(min), dateTime(max));
            }
        };
    }

    @Override
    public NotBetweenCriterionOperation<T> notBetween() {
        return new NotBetweenCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T min, T max) {
                return String.format("%s BETWEEN '%s' AND '%s'", getPropertyColumn(property, functions), dateTime(min), dateTime(max));
            }
        };
    }

    protected abstract String date(T date);

    protected abstract String dateTime(T date);
}
