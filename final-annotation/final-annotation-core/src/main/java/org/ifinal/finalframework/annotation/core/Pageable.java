package org.ifinal.finalframework.annotation.core;

import org.springframework.lang.Nullable;

/**
 * The interface for query with {@link Pageable}, do page query only when the {@linkplain #getPage() page} and
 * {@linkplain #getSize()} are not null, and do count query only then the {@linkplain #getCount() count} is {@code
 * true}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Pageable extends IQuery {

    /**
     * return the page number.
     *
     * @return the page number.
     */
    @Nullable
    Integer getPage();

    void setPage(@Nullable Integer page);

    /**
     * return the page size.
     *
     * @return the page size.
     */
    @Nullable
    Integer getSize();

    void setSize(@Nullable Integer size);

    /**
     * return {@code true} if need do count, otherwise {@code false}, default need count.
     *
     * @return {@code true} if need do count, otherwise {@code false}.
     */
    @Nullable
    default Boolean getCount() {
        return Boolean.TRUE;
    }

    void setCount(@Nullable Boolean count);

}
