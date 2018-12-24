package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.core.Assert;
import cn.com.likly.finalframework.core.Streable;
import cn.com.likly.finalframework.data.domain.enums.SetOperation;
import cn.com.likly.finalframework.data.mapping.Property;
import lombok.NonNull;

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
public class Update implements Streable<UpdateSet>, Iterable<UpdateSet> {

    private final Set<String> keysToUpdate;
    private final Map<String, UpdateSet> modifierOps;

    public Update() {
        this.keysToUpdate = new LinkedHashSet<>();
        this.modifierOps = new LinkedHashMap<>();
    }

    private Update(Collection<UpdateSet> updateSets) {
        Assert.isEmpty(updateSets, "updateSets must be not null and empty!");
        this.modifierOps = updateSets.stream().collect(Collectors.toMap(it -> it.getProperty().getName(), Function.identity()));
        this.keysToUpdate = modifierOps.keySet();
    }

    public static Update update(UpdateSet... updateSets) {
        return new Update(Arrays.asList(updateSets));
    }

    public static Update update(Collection<UpdateSet> updateSets) {
        return new Update(updateSets);
    }


    public Update set(@NonNull Property property, @NonNull Object value) {
        keysToUpdate.add(property.getName());
        modifierOps.put(property.getName(), new UpdateSet(property, SetOperation.EQUAL, value));
        return this;
    }

    public Update inc(@NonNull Property property) {
        keysToUpdate.add(property.getName());
        modifierOps.put(property.getName(), new UpdateSet(property, SetOperation.EQUAL, 1));
        return this;
    }

    public Update incy(@NonNull Property property, @NonNull Number value) {
        keysToUpdate.add(property.getName());
        modifierOps.put(property.getName(), new UpdateSet(property, SetOperation.EQUAL, value));
        return this;
    }

    public Update dec(@NonNull Property property) {
        keysToUpdate.add(property.getName());
        modifierOps.put(property.getName(), new UpdateSet(property, SetOperation.EQUAL, 1));
        return this;
    }

    public Update decy(@NonNull Property property, @NonNull Number value) {
        keysToUpdate.add(property.getName());
        modifierOps.put(property.getName(), new UpdateSet(property, SetOperation.EQUAL, value));
        return this;
    }

    public boolean contains(String property) {
        return modifierOps.containsKey(property);
    }

    public UpdateSet getUpdateSet(String property) {
        return modifierOps.get(property);
    }

    public Collection<UpdateSet> getUpdateSets() {
        return modifierOps.values();
    }

    @Override
    public Stream<UpdateSet> stream() {
        return modifierOps.values().stream();
    }

    @Override
    public Iterator<UpdateSet> iterator() {
        return modifierOps.values().iterator();
    }


}
