package org.ifinal.finalframework.origin.result;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ifinal.finalframework.origin.IPage;

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

