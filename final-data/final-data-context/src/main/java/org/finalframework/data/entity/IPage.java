package org.finalframework.data.entity;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-10 16:45:38
 * @since 1.0
 */
public interface IPage<T> extends Serializable {

    Integer getPage();

    Integer getSize();

    Long getTotal();

    Integer getPages();

    Boolean getFirstPage();

    Boolean getLastPage();

    T getResult();

}
