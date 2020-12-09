package org.ifinal.finalframework.annotation.core.result;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ifinal.finalframework.annotation.core.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Page<T extends Serializable> extends Pagination implements IPage<T> {
    private List<T> data;
}

