package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.SetOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 21:36
 * @since 1.0
 */
@Data
@SuppressWarnings("unused")
public class Update<T> implements Iterable<Update.UpdateSet<T>> {

    private Set<T> keysToUpdate = new HashSet<>();
    private Map<T, UpdateSet<T>> modifierOps = new LinkedHashMap<>();

    public static <T> Update<T> update(T key, Object value) {
        return new Update<T>().set(key, value);
    }


    public Update<T> set(@NonNull T property, Object value) {
        addToSet(property, SetOperation.EQUAL, value);
        return this;
    }

    public Update<T> inc(@NonNull T property) {
        addToSet(property, SetOperation.INC, 1);
        return this;
    }

    public Update<T> incy(@NonNull T property, Number value) {
        addToSet(property, SetOperation.EQUAL, value);
        return this;
    }

    public Update<T> dec(@NonNull T property) {
        addToSet(property, SetOperation.DEC, 1);
        return this;
    }

    public Update<T> decy(@NonNull T property, Number value) {
        addToSet(property, SetOperation.DECY, value);
        return this;
    }

    private void addToSet(T property, SetOperation operation, Object value) {
        this.keysToUpdate.add(property);
        this.modifierOps.put(property, new UpdateSet<>(property, operation, value));
    }

    public Stream<UpdateSet<T>> stream() {
        return modifierOps.values().stream();
    }

    @Override
    public Iterator<UpdateSet<T>> iterator() {
        return modifierOps.values().iterator();
    }


    @Data
    @AllArgsConstructor
    public static class UpdateSet<T> implements Serializable {
        private final T property;
        private final SetOperation operation;
        private final Object value;

        public boolean isNull() {
            return value == null;
        }
    }
}
