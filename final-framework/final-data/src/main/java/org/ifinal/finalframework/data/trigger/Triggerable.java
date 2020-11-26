package org.ifinal.finalframework.data.trigger;

import org.ifinal.finalframework.annotation.IEntity;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Triggerable<ID extends Serializable, T extends IEntity<ID>> extends
        InsertTrigger<ID, T>, UpdateTrigger<ID, T>, DeleteTrigger<ID, T>, SelectTrigger<ID, T> {
}
