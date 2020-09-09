

package org.finalframework.data.trigger;

import org.finalframework.annotation.IEntity;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 21:53:53
 * @since 1.0
 */
public interface Triggerable<ID extends Serializable, T extends IEntity<ID>> extends
        InsertTrigger<ID, T>, UpdateTrigger<ID, T>, DeleteTrigger<ID, T>, SelectTrigger<ID, T> {
}
