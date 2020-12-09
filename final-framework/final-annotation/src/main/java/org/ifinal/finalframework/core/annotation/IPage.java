package org.ifinal.finalframework.core.annotation;


import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPage<T extends Serializable> extends IData<List<T>>, IPagination {

}
