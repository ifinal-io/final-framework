package org.finalframework.data.result;

import lombok.Data;
import org.finalframework.data.entity.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:09
 * @since 1.0
 */
@Data
public class Page<E extends Serializable> extends PageInfo implements IPage<List<E>> {
    /**
     * 结果集
     */
    private List<E> result;

    public Page() {
    }

    private Page(Builder<E> builder) {
        setPage(builder.page);
        setSize(builder.size);
        setPages(builder.pages);
        setTotal(builder.total);
        setFirstPage(builder.firstPage);
        setLastPage(builder.lastPage);
        this.result = builder.result;
    }

    public static <E extends Serializable, T extends Serializable> Builder<E> builder() {
        return new Builder<>();
    }

    public static class Builder<E extends Serializable> implements org.finalframework.core.Builder<Page<E>> {
        private Integer page;
        private Integer size;
        private Integer pages;
        private Long total;
        private List<E> result;
        private Boolean firstPage;
        private Boolean lastPage;

        public Builder<E> page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder<E> size(Integer size) {
            this.size = size;
            return this;
        }

        public Builder<E> pages(Integer pages) {
            this.pages = pages;
            return this;
        }

        public Builder<E> total(Long total) {
            this.total = total;
            return this;
        }


        public Builder<E> result(List<E> result) {
            this.result = result;
            return this;
        }

        public Builder<E> firstPage(boolean firstPage) {
            this.firstPage = firstPage;
            return this;
        }

        public Builder<E> lastPage(boolean lastPage) {
            this.lastPage = lastPage;
            return this;
        }

        @Override
        public Page<E> build() {
            return new Page<E>(this);
        }
    }
}
