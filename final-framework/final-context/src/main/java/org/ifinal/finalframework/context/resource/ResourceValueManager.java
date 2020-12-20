package org.ifinal.finalframework.context.resource;

import java.util.Collection;

/**
 * ResourceValueManager.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ResourceValueManager {

    Collection<ResourceValueHolder> getResourceValueHolders(String key);

    void addResourceValueHolder(String key, ResourceValueHolder holder);

    void setValue(String key, String value);

}
