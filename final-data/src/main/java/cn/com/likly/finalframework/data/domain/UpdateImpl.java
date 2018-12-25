package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.core.Assert;
import cn.com.likly.finalframework.data.domain.enums.SetOperation;
import cn.com.likly.finalframework.data.mapping.Property;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 10:38
 * @since 1.0
 */
@SuppressWarnings("unused")
class UpdateImpl implements Update, Map<String, UpdateSet> {
    private final Map<String, UpdateSet> modifierOps;

    private UpdateImpl() {
        this.modifierOps = new LinkedHashMap<>();
    }

    private UpdateImpl(Collection<UpdateSet> updateSets) {
        this.modifierOps = Assert.isEmpty(updateSets) ? new LinkedHashMap<>()
                : updateSets.stream().collect(Collectors.toMap(it -> it.getProperty().getName(), Function.identity()));
    }

    public static UpdateImpl update() {
        return new UpdateImpl();
    }

    public static UpdateImpl update(UpdateSet... updateSets) {
        return new UpdateImpl(Arrays.asList(updateSets));
    }

    public static UpdateImpl update(Collection<UpdateSet> updateSets) {
        return new UpdateImpl(updateSets);
    }


    public UpdateImpl set(@NonNull Property property, @NonNull Object value) {
        modifierOps.put(property.getName(), new UpdateSet(property, SetOperation.EQUAL, value));
        return this;
    }

    public UpdateImpl inc(@NonNull Property property) {
        modifierOps.put(property.getName(), new UpdateSet(property, SetOperation.INC, 1));
        return this;
    }

    public UpdateImpl incr(@NonNull Property property, @NonNull Number value) {
        modifierOps.put(property.getName(), new UpdateSet(property, SetOperation.INCR, value));
        return this;
    }

    public UpdateImpl dec(@NonNull Property property) {
        modifierOps.put(property.getName(), new UpdateSet(property, SetOperation.DEC, 1));
        return this;
    }

    public UpdateImpl decr(@NonNull Property property, @NonNull Number value) {
        modifierOps.put(property.getName(), new UpdateSet(property, SetOperation.DECR, value));
        return this;
    }

    public boolean contains(String property) {
        return modifierOps.containsKey(property);
    }

    @Override
    public Stream<UpdateSet> stream() {
        return modifierOps.values().stream();
    }

    @Override
    public Iterator<UpdateSet> iterator() {
        return modifierOps.values().iterator();
    }

    @Override
    public int size() {
        return modifierOps.size();
    }

    @Override
    public boolean isEmpty() {
        return modifierOps.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return modifierOps.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return modifierOps.containsValue(value);
    }

    @Override
    public UpdateSet get(Object key) {
        return modifierOps.get(key);
    }

    @Nullable
    @Override
    public UpdateSet put(String key, UpdateSet value) {
        return modifierOps.put(key, value);
    }

    @Override
    public UpdateSet remove(Object key) {
        return modifierOps.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends UpdateSet> m) {
        modifierOps.putAll(m);
    }

    @Override
    public void clear() {
        modifierOps.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return modifierOps.keySet();
    }

    @NotNull
    @Override
    public Collection<UpdateSet> values() {
        return modifierOps.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, UpdateSet>> entrySet() {
        return modifierOps.entrySet();
    }
}
