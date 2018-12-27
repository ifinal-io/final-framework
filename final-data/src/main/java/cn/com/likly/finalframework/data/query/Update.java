package cn.com.likly.finalframework.data.query;

import cn.com.likly.finalframework.core.Streable;
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

    Update set(@NonNull QProperty property, @NonNull Object value);

    Update inc(@NonNull QProperty property);

    Update incr(@NonNull QProperty property, @NonNull Number value);

    Update dec(@NonNull QProperty property);

    Update decr(@NonNull QProperty property, @NonNull Number value);


}
