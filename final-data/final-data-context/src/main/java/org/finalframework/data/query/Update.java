package org.finalframework.data.query;

import lombok.NonNull;
import org.finalframework.core.Streamable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 10:38
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface Update extends Streamable<UpdateSetOperation>, Iterable<UpdateSetOperation>, SqlNode {

    static Update update() {
        return UpdateImpl.update();
    }

    static Update update(UpdateSetOperation... updateSets) {
        return UpdateImpl.update(Arrays.asList(updateSets));
    }

    static Update update(Collection<UpdateSetOperation> updateSets) {
        return UpdateImpl.update(updateSets);
    }

    Update set(@NonNull QProperty<?> property, @NonNull Object value);

    Update inc(@NonNull QProperty<?> property);

    Update incr(@NonNull QProperty<?> property, @NonNull Number value);

    Update dec(@NonNull QProperty<?> property);

    Update decr(@NonNull QProperty<?> property, @NonNull Number value);


    @Override
    default void apply(Node parent, String expression) {
        final Document document = parent.getOwnerDocument();
        final Element set = document.createElement("set");
        this.forEach(item -> item.apply(set, expression));
        parent.appendChild(set);
    }
}
