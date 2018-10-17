package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.SetOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 21:36
 * @since 1.0
 */
@Data
public class Update {

    private Set<String> keysToUpdate = new HashSet<>();
    private Map<String, UpdateSet> modifierOps = new LinkedHashMap<>();

    public static Update update(String key, Object value) {
        return new Update().set(key, value);
    }


    public Update set(@NonNull String property, Object value) {
        addToSet(property, SetOperation.EQUAL, value);
        return this;
    }

    public Update inc(@NonNull String property) {
        addToSet(property, SetOperation.INC, 1);
        return this;
    }

    public Update incy(@NonNull String property, Number value) {
        addToSet(property, SetOperation.EQUAL, value);
        return this;
    }

    public Update dec(@NonNull String property) {
        addToSet(property, SetOperation.DEC, 1);
        return this;
    }

    public Update decy(@NonNull String property, Number value) {
        addToSet(property, SetOperation.DECY, value);
        return this;
    }

    public void addToSet(String property, SetOperation operation, Object value) {
        this.keysToUpdate.add(property);
        this.modifierOps.put(property, new UpdateSet(property, operation, value));
    }


    @Data
    @AllArgsConstructor
    public static class UpdateSet implements Serializable {
        private final String property;
        private final SetOperation operation;
        private final Object value;
    }
}
