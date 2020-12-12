package org.ifinal.finalframework.annotation.core;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * The interface of pagination info which query with {@link Pageable}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPagination extends Serializable {

    /**
     * return the page number.
     *
     * @return the page number.
     */
    @NonNull
    Integer getPage();

    /**
     * return the size of page in this query.
     *
     * @return the size of page in this query.
     */
    @NonNull
    Integer getSize();

    /**
     * return the total counts of this query.
     *
     * @return the total counts of this query.
     */
    @Nullable
    Long getTotal();

    /**
     * return the total pages of this query.
     *
     * @return the total pages of this query.
     */
    @Nullable
    Integer getPages();

    /**
     * return {@code true} if is the first page.
     *
     * @return {@code true} if is the first page.
     */
    @Nullable
    Boolean getFirstPage();

    /**
     * return {@code true} if this is the last page,
     *
     * @return {@code true} if this is the last page,
     */
    @Nullable
    Boolean getLastPage();

}
