package org.ifinal.finalframework.context.resource;

import java.util.List;
import lombok.Getter;
import org.ifinal.finalframework.context.annotation.ResourceValue;
import org.springframework.stereotype.Component;

/**
 * ResourceValueEntity.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@ResourceValue("config")
@Getter
public class ResourceValueEntity implements ResourceInterface {

    @ResourceValue("name")
    private String name;

    private List<Integer> stores;

    @Override
    public void onResourceValueChanged(final List<Integer> stores) {
        this.stores = stores;
    }

}
