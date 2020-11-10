package org.finalframework.annotation;


import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-10 16:45:38
 * @since 1.0
 */
public interface IPage<T extends Serializable> extends IData<List<T>>, IPagination {

}
