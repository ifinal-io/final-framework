package org.ifinal.finalframework.data.query.operation;


import org.ifinal.finalframework.data.query.criterion.Criterion;
import org.ifinal.finalframework.data.query.criterion.JsonContainsCriterion;
import org.ifinal.finalframework.data.query.criterion.function.CriterionFunction;
import org.ifinal.finalframework.data.query.criterion.function.SimpleCriterionFunction;
import org.ifinal.finalframework.data.query.criterion.function.SingleCriterionFunction;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JsonOperations {

    public static Criterion contains(@NonNull Object doc, @NonNull Object value, @NonNull String path) {
        return JsonContainsCriterion.contains(doc, value, path);
    }

    public static Criterion notContains(@NonNull Object doc, @NonNull Object value, @NonNull String path) {
        return JsonContainsCriterion.notContains(doc, value, path);
    }

    public static CriterionFunction extract(@NonNull Object doc, @NonNull String path) {
        return new SingleCriterionFunction(doc, JsonOperation.JSON_EXTRACT, path);
    }

    public static CriterionFunction unquote(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_UNQUOTE);
    }


    public static CriterionFunction keys(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_KEYS);
    }


    public static CriterionFunction depth(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_DEPTH);
    }


    public static CriterionFunction length(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_LENGTH);
    }

    public static CriterionFunction object(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_OBJECT);
    }

    public static CriterionFunction array(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_ARRAY);
    }


}

