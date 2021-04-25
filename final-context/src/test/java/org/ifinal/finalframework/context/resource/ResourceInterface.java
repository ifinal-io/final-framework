package org.ifinal.finalframework.context.resource;

import java.util.List;
import org.ifinal.finalframework.context.annotation.ResourceValue;

/**
 * ResourceInterface.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ResourceInterface {

    @ResourceValue("stores")
    void onResourceValueChanged(List<Integer> stores);

}
