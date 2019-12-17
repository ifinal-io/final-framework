package org.finalframework.data.query.criterion.operation;


import org.finalframework.data.entity.enums.IEnum;
import org.finalframework.data.query.criterion.CriterionOperation;
import org.finalframework.data.query.criterion.operator.CriterionOperator;
import org.finalframework.data.query.criterion.FunctionCriterion;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.SimpleCriterionOperations;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-09 00:30:58
 * @since 1.0
 */
public class EnumCriterionOperations<T extends IEnum> extends BaseCriterionOperations<T> implements SimpleCriterionOperations<T> {

    public static final EnumCriterionOperations<IEnum> INSTANCE = new EnumCriterionOperations<>(IEnum.class);
    private final Class<T> type;

    public EnumCriterionOperations(Class<T> type) {
        super(type, 16);
        this.type = type;
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
                final Object code = value.getCode();
                if (code instanceof String) {
                    return getRequiredCriterionOperation(StringCriterionOperations.INSTANCE.eq(), operator)
                            .format(property, functions, operator, (String) code);
                } else if (code instanceof Date) {
                    return getRequiredCriterionOperation(DateCriterionOperations.INSTANCE.eq(), operator)
                            .format(property, functions, operator, (Date) code);
                } else {
                    return getRequiredCriterionOperation(ObjectCriterionOperations.INSTANCE.eq(), operator)
                            .format(property, functions, operator, code);
                }
            }
        };
    }

    @Override
    public NotEqualCriterionOperation<T> neq() {
        return new NotEqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                final Object code = value.getCode();
                if (code instanceof String) {
                    return getRequiredCriterionOperation(StringCriterionOperations.INSTANCE.neq(), operator)
                            .format(property, functions, operator, (String) code);
                } else if (code instanceof Date) {
                    return getRequiredCriterionOperation(DateCriterionOperations.INSTANCE.neq(), operator)
                            .format(property, functions, operator, (Date) code);
                } else {
                    return getRequiredCriterionOperation(ObjectCriterionOperations.INSTANCE.neq(), operator)
                            .format(property, functions, operator, code);
                }
            }
        };
    }

    @Override
    public GreaterThanCriterionOperation<T> gt() {
        return new GreaterThanCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                final Object code = value.getCode();
                if (code instanceof String) {
                    return getRequiredCriterionOperation(StringCriterionOperations.INSTANCE.gt(), operator)
                            .format(property, functions, operator, (String) code);
                } else if (code instanceof Date) {
                    return getRequiredCriterionOperation(DateCriterionOperations.INSTANCE.gt(), operator)
                            .format(property, functions, operator, (Date) code);
                } else {
                    return getRequiredCriterionOperation(ObjectCriterionOperations.INSTANCE.gt(), operator)
                            .format(property, functions, operator, code);
                }
            }
        };
    }

    @Override
    public GreaterThanEqualCriterionOperation<T> gte() {
        return new GreaterThanEqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                final Object code = value.getCode();
                if (code instanceof String) {
                    return getRequiredCriterionOperation(StringCriterionOperations.INSTANCE.gte(), operator)
                            .format(property, functions, operator, (String) code);
                } else if (code instanceof Date) {
                    return getRequiredCriterionOperation(DateCriterionOperations.INSTANCE.gte(), operator)
                            .format(property, functions, operator, (Date) code);
                } else {
                    return getRequiredCriterionOperation(ObjectCriterionOperations.INSTANCE.gte(), operator)
                            .format(property, functions, operator, code);
                }
            }
        };
    }

    @Override
    public LessThanCriterionOperation<T> lt() {
        return new LessThanCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                final Object code = value.getCode();
                if (code instanceof String) {
                    return getRequiredCriterionOperation(StringCriterionOperations.INSTANCE.lt(), operator)
                            .format(property, functions, operator, (String) code);
                } else if (code instanceof Date) {
                    return getRequiredCriterionOperation(DateCriterionOperations.INSTANCE.lt(), operator)
                            .format(property, functions, operator, (Date) code);
                } else {
                    return getRequiredCriterionOperation(ObjectCriterionOperations.INSTANCE.lt(), operator)
                            .format(property, functions, operator, code);
                }
            }
        };
    }

    @Override
    public LessThanEqualCriterionOperation<T> lte() {
        return new LessThanEqualCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T value) {
                final Object code = value.getCode();
                if (code instanceof String) {
                    return getRequiredCriterionOperation(StringCriterionOperations.INSTANCE.lte(), operator)
                            .format(property, functions, operator, (String) code);
                } else if (code instanceof Date) {
                    return getRequiredCriterionOperation(DateCriterionOperations.INSTANCE.lte(), operator)
                            .format(property, functions, operator, (Date) code);
                } else {
                    return getRequiredCriterionOperation(ObjectCriterionOperations.INSTANCE.lte(), operator)
                            .format(property, functions, operator, code);
                }
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public InCriterionOperation<T> in() {
        return new InCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, Collection<T> value) {
                final List codes = value.stream().map(IEnum::getCode).collect(Collectors.toList());
                final Object code = codes.get(0);
                if (code instanceof String) {
                    return getRequiredCriterionOperation(StringCriterionOperations.INSTANCE.in(), operator)
                            .format(property, functions, operator, (Collection<String>) codes);
                } else if (code instanceof Date) {
                    return getRequiredCriterionOperation(DateCriterionOperations.INSTANCE.in(), operator)
                            .format(property, functions, operator, (Collection<Date>) codes);
                } else {
                    return getRequiredCriterionOperation(ObjectCriterionOperations.INSTANCE.in(), operator)
                            .format(property, functions, operator, codes);
                }
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public NotInCriterionOperation<T> nin() {
        return new NotInCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, Collection<T> value) {
                final List codes = value.stream().map(IEnum::getCode).collect(Collectors.toList());
                final Object code = codes.get(0);
                if (code instanceof String) {
                    return getRequiredCriterionOperation(StringCriterionOperations.INSTANCE.nin(), operator)
                            .format(property, functions, operator, (Collection<String>) codes);
                } else if (code instanceof Date) {
                    return getRequiredCriterionOperation(DateCriterionOperations.INSTANCE.nin(), operator)
                            .format(property, functions, operator, (Collection<Date>) codes);
                } else {
                    return getRequiredCriterionOperation(ObjectCriterionOperations.INSTANCE.nin(), operator)
                            .format(property, functions, operator, codes);
                }
            }
        };
    }

    @Override
    public BetweenCriterionOperation<T> between() {
        return new BetweenCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T min, T max) {
                final Object minCode = min.getCode();
                final Object maxCode = max.getCode();
                if (minCode instanceof String) {
                    return getRequiredCriterionOperation(StringCriterionOperations.INSTANCE.between(), operator)
                            .format(property, functions, operator, (String) minCode, (String) maxCode);
                } else if (minCode instanceof Date) {
                    return getRequiredCriterionOperation(DateCriterionOperations.INSTANCE.between(), operator)
                            .format(property, functions, operator, (Date) minCode, (Date) maxCode);
                } else {
                    return getRequiredCriterionOperation(ObjectCriterionOperations.INSTANCE.between(), operator)
                            .format(property, functions, operator, minCode, maxCode);
                }
            }
        };
    }

    @Override
    public NotBetweenCriterionOperation<T> notBetween() {
        return new NotBetweenCriterionOperation<T>() {
            @Override
            public String format(QProperty property, Collection<FunctionCriterion> functions, CriterionOperator operator, T min, T max) {
                final Object minCode = min.getCode();
                final Object maxCode = max.getCode();
                if (minCode instanceof String) {
                    return getRequiredCriterionOperation(StringCriterionOperations.INSTANCE.notBetween(), operator)
                            .format(property, functions, operator, (String) minCode, (String) maxCode);
                } else if (minCode instanceof Date) {
                    return getRequiredCriterionOperation(DateCriterionOperations.INSTANCE.notBetween(), operator)
                            .format(property, functions, operator, (Date) minCode, (Date) maxCode);
                } else {
                    return getRequiredCriterionOperation(ObjectCriterionOperations.INSTANCE.notBetween(), operator)
                            .format(property, functions, operator, minCode, maxCode);
                }
            }
        };
    }

    @Override
    public Class<T> type() {
        return type;
    }

    @NonNull
    private <CO extends CriterionOperation> CO getRequiredCriterionOperation(@NonNull CO operation, CriterionOperator operator) {
        if (operation == null) {
            throw new UnsupportedOperationException(String.format("枚举类型不支持操作类型 %s", operator.name()));
        }
        return operation;
    }


}
