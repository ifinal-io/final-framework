package com.ilikly.finalframework.data.query.criterion.operation;


import com.ilikly.finalframework.core.formatter.DateFormatter;
import com.ilikly.finalframework.data.query.CriterionOperator;
import com.ilikly.finalframework.data.query.QProperty;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-08 22:13:20
 * @since 1.0
 */
public class DateCriterionOperations extends BaseCriterionOperators<Date> implements com.ilikly.finalframework.data.query.criterion.DateCriterionOperations<Date> {

    public static final DateCriterionOperations INSTANCE = new DateCriterionOperations();

    public DateCriterionOperations() {
        super(Date.class, 32);
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
    public BeforeCriterionOperation<Date> before() {
        return new BeforeCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                return String.format("%s < '%s'", getPropertyColumn(property), dateTime(value));
            }
        };
    }

    @Override
    public AfterCriterionOperation<Date> after() {
        return new AfterCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                return String.format("%s > '%s'", getPropertyColumn(property), dateTime(value));
            }
        };
    }

    @Override
    public DateEqualCriterionOperation<Date> dateEqual() {
        return new DateEqualCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                final String column = getPropertyColumn(property);
                return String.format("DATE(%s) = '%s'", column, date(value));
            }

        };
    }

    @Override
    public NotDateEqualCriterionOperation<Date> notDateEqual() {
        return new NotDateEqualCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                final String column = getPropertyColumn(property);
                return String.format("DATE(%s) != '%s'", column, date(value));
            }
        };
    }

    @Override
    public DateBeforeCriterionOperation<Date> dateBefore() {
        return new DateBeforeCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                final String column = getPropertyColumn(property);
                return String.format("DATE(%s) < '%s'", column, dateTime(value));
            }
        };
    }

    @Override
    public DateAfterCriterionOperation<Date> dateAfter() {
        return new DateAfterCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                final String column = getPropertyColumn(property);
                return String.format("DATE(%s) > '%s'", column, dateTime(value));
            }
        };
    }

    @Override
    public DateBetweenCriterionOperation<Date> dateBetween() {
        return new DateBetweenCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date min, Date max) {
                final String column = getPropertyColumn(property);
                return String.format("DATE(%s) BETWEEN '%s' AND '%s'", column, dateTime(min), dateTime(max));
            }
        };
    }

    @Override
    public NotDateBetweenCriterionOperation<Date> notDateBetween() {
        return new NotDateBetweenCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date min, Date max) {
                final String column = getPropertyColumn(property);
                return String.format("DATE(%s) NOT BETWEEN '%s' AND '%s'", column, dateTime(min), dateTime(max));
            }
        };
    }

    @Override
    public EqualCriterionOperation<Date> eq() {
        return new EqualCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                return String.format("%s = '%s'", getPropertyColumn(property), dateTime(value));
            }
        };
    }

    @Override
    public NotEqualCriterionOperation<Date> neq() {
        return new NotEqualCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                return String.format("%s != '%s'", getPropertyColumn(property), dateTime(value));
            }
        };
    }

    @Override
    public GreaterThanCriterionOperation<Date> gt() {
        return new GreaterThanCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                return String.format("%s > '%s'", getPropertyColumn(property), dateTime(value));
            }
        };
    }

    @Override
    public GreaterThanEqualCriterionOperation<Date> gte() {
        return new GreaterThanEqualCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                return String.format("%s >= '%s'", getPropertyColumn(property), dateTime(value));
            }
        };
    }

    @Override
    public LessThanCriterionOperation<Date> lt() {
        return new LessThanCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                return String.format("%s < '%s'", getPropertyColumn(property), dateTime(value));
            }
        };
    }

    @Override
    public LessThanEqualCriterionOperation<Date> lte() {
        return new LessThanEqualCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date value) {
                return String.format("%s <= '%s'", getPropertyColumn(property), dateTime(value));
            }
        };
    }

    @Override
    public InCriterionOperation<Date> in() {
        return new InCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Collection<Date> value) {
                return String.format("%s IN %s", getPropertyColumn(property),
                        value.stream().map(it -> String.format("'%s'", dateTime(it))).collect(Collectors.joining(",", "(", ")")));
            }
        };
    }

    @Override
    public NotInCriterionOperation<Date> nin() {
        return new NotInCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Collection<Date> value) {
                return String.format("%s NOT IN %s", getPropertyColumn(property),
                        value.stream().map(it -> String.format("'%s'", dateTime(it))).collect(Collectors.joining(",", "(", ")")));
            }
        };
    }

    @Override
    public BetweenCriterionOperation<Date> between() {
        return new BetweenCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date min, Date max) {
                return String.format("%s BETWEEN '%s' AND '%s'", getPropertyColumn(property), dateTime(min), dateTime(max));
            }
        };
    }

    @Override
    public NotBetweenCriterionOperation<Date> notBetween() {
        return new NotBetweenCriterionOperation<Date>() {
            @Override
            public String format(QProperty property, CriterionOperator operator, Date min, Date max) {
                return String.format("%s BETWEEN '%s' AND '%s'", getPropertyColumn(property), dateTime(min), dateTime(max));
            }
        };
    }

    private String date(Date date) {
        return DateFormatter.YYYY_MM_DD.format(date);
    }

    private String dateTime(Date date) {
        return DateFormatter.YYYY_MM_DD_HH_MM_SS.format(date);
    }
}
