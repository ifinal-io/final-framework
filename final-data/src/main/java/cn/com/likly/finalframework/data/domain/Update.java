package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.core.Streable;
import cn.com.likly.finalframework.data.mapping.Property;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 10:38
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface Update extends Streable<UpdateSet>, Iterable<UpdateSet> {

    static Update update() {
        return UpdateImpl.update();
    }

    static Update update(UpdateSet... updateSets) {
        return UpdateImpl.update(Arrays.asList(updateSets));
    }

    static Update update(Collection<UpdateSet> updateSets) {
        return UpdateImpl.update(updateSets);
    }

    Update set(@NonNull Property property, @NonNull Object value);

    Update inc(@NonNull Property property);

    Update incr(@NonNull Property property, @NonNull Number value);

    Update dec(@NonNull Property property);

    Update decr(@NonNull Property property, @NonNull Number value);


}
