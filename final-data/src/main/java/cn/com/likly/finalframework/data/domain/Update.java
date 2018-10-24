package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.SetOperation;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;
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
public class Update implements Iterable<Update.UpdateSet> {

    private Set<PropertyHolder> keysToUpdate = new HashSet<>();
    private Map<PropertyHolder, UpdateSet> modifierOps = new LinkedHashMap<>();

    public static Update update(PropertyHolder key, Object value) {
        return new Update().set(key, value);
    }


    public Update set(@NonNull PropertyHolder property, Object value) {
        addToSet(property, SetOperation.EQUAL, value);
        return this;
    }

    public Update inc(@NonNull PropertyHolder property) {
        addToSet(property, SetOperation.INC, 1);
        return this;
    }

    public Update incy(@NonNull PropertyHolder property, Number value) {
        addToSet(property, SetOperation.EQUAL, value);
        return this;
    }

    public Update dec(@NonNull PropertyHolder property) {
        addToSet(property, SetOperation.DEC, 1);
        return this;
    }

    public Update decy(@NonNull PropertyHolder property, Number value) {
        addToSet(property, SetOperation.DECY, value);
        return this;
    }

    private void addToSet(PropertyHolder property, SetOperation operation, Object value) {
        this.keysToUpdate.add(property);
        this.modifierOps.put(property, new UpdateSet(property, operation, value));
    }

    public Stream<UpdateSet> stream() {
        return modifierOps.values().stream();
    }

    @Override
    public Iterator<UpdateSet> iterator() {
        return modifierOps.values().iterator();
    }


    @Data
    @AllArgsConstructor
    public static class UpdateSet implements Serializable {
        private final PropertyHolder property;
        private final SetOperation operation;
        private final Object value;

        public boolean isNull() {
            return value == null;
        }
    }
}
