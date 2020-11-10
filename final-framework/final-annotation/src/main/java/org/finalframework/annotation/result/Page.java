package org.finalframework.annotation.result;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.finalframework.annotation.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-10 17:12:50
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Page<T extends Serializable> extends Pagination implements IPage<T> {
    private List<T> data;
}

