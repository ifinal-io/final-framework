package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.data.entity.enums.IEnum;
import com.ilikly.finalframework.data.query.operation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 15:05:19
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class CriterionOperationRegistry {

    private static List<Class> primaryClass = Arrays.asList(
            byte.class, Byte.class, short.class, Short.class,
            char.class, Character.class, boolean.class, Boolean.class,
            int.class, Integer.class, long.class, Long.class,
            float.class, Float.class, double.class, Double.class,
            String.class
    );

    private static final CriterionOperationRegistry INSTANCE = new CriterionOperationRegistry();

    public static CriterionOperationRegistry getInstance(){
        return INSTANCE;
    }

    private CriterionOperationRegistry(){}

    private final Map<String, Map<Class, CriterionOperation>> cache = new ConcurrentHashMap<>(32);

    {

        primaryClass.forEach(clazz -> registerCriterionOperation(clazz, EqualCriterionOperation.INSTANCE));
        primaryClass.forEach(clazz -> registerCriterionOperation(clazz, NotEqualCriterionOperation.INSTANCE));
        primaryClass.forEach(clazz -> registerCriterionOperation(clazz, GreaterThanCriterionOperation.INSTANCE));
        primaryClass.forEach(clazz -> registerCriterionOperation(clazz, GreaterEqualThanCriterionOperation.INSTANCE));
        primaryClass.forEach(clazz -> registerCriterionOperation(clazz, LessThanCriterionOperation.INSTANCE));
        primaryClass.forEach(clazz -> registerCriterionOperation(clazz, LessEqualThanCriterionOperation.INSTANCE));
        primaryClass.forEach(clazz -> registerCriterionOperation(clazz, InCriterionOperation.INSTANCE));
        primaryClass.forEach(clazz -> registerCriterionOperation(clazz, NotInCriterionOperation.INSTANCE));
        primaryClass.forEach(clazz -> registerCriterionOperation(clazz, BetweenCriterionOperation.INSTANCE));
        primaryClass.forEach(clazz -> registerCriterionOperation(clazz, NotBetweenCriterionOperation.INSTANCE));


        registerCriterionOperation(String.class, StartWithCriterionOperation.INSTANCE);
        registerCriterionOperation(String.class, NotStartWithCriterionOperation.INSTANCE);
        registerCriterionOperation(String.class, EndWithCriterionOperation.INSTANCE);
        registerCriterionOperation(String.class, NotEndWithCriterionOperation.INSTANCE);
        registerCriterionOperation(String.class, ContainsCriterionOperation.INSTANCE);
        registerCriterionOperation(String.class, NotContainsCriterionOperation.INSTANCE);
        registerCriterionOperation(String.class, LikeCriterionOperation.INSTANCE);
        registerCriterionOperation(String.class, NotLikeCriterionOperation.INSTANCE);

        registerCriterionOperation(Date.class, DateBeforeCriterionOperation.INSTANCE);
        registerCriterionOperation(Date.class, DateAfterCriterionOperation.INSTANCE);
        registerCriterionOperation(Date.class, DateBetweenCriterionOperation.INSTANCE);
        registerCriterionOperation(Date.class, NotDateBetweenCriterionOperation.INSTANCE);


    }

    public static void main(String[] args) {
        new CriterionOperationRegistry();
    }

    public <T> void registerCriterionOperation(Class<T> type, CriterionOperation<T> criterionOperation) {
        Map<Class, CriterionOperation> nameTypeOperationCache = getNameTypeOperationCache(criterionOperation.name());
        nameTypeOperationCache.put(type, criterionOperation);
    }


    @SuppressWarnings("unchecked")
    public <T> CriterionOperation<T> getCriterionOperation(String operationName, Class<T> type) {
        try {
            CriterionOperations operations = CriterionOperations.valueOf(operationName);
            switch (operations) {
                case NULL:
                    return NullCriterionOperation.INSTANCE;
                case NOT_NULL:
                    return NotNullCriterionOperation.INSTANCE;
            }
        } catch (Exception e) {
            //ignore
        }
        Class clazz = type;

        if (type.isEnum()) {
            if (IEnum.class.isAssignableFrom(type)) {
                IEnum ienum = (IEnum) type.getEnumConstants()[0];
                clazz = ienum.getCode().getClass();
            }
        }

        Map<Class, CriterionOperation> nameTypeOperationCache = getNameTypeOperationCache(operationName);
        CriterionOperation criterionOperation = nameTypeOperationCache.get(clazz);
        if (criterionOperation == null) {
            throw new IllegalArgumentException(String.format("not found criterion operation of name =%s and type = %s", operationName, type.getCanonicalName()));
        }
        return criterionOperation;
    }

    private Map<Class, CriterionOperation> getNameTypeOperationCache(String name) {


        if (!cache.containsKey(name)) {
            cache.put(name, new ConcurrentHashMap<>(64));
        }
        return cache.get(name);
    }

}
