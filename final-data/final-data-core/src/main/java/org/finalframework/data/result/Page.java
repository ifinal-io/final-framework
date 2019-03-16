package org.finalframework.data.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:09
 * @since 1.0
 */
@Data
public class Page<T> implements Serializable {

    private final Integer page;
    private final Integer size;
    private final Integer pages;
    private final Long total;
    private final List<T> result;
    private final Boolean firstPage;
    private final Boolean lastPage;

    private Page(Builder<T> builder) {
        this.page = builder.page;
        this.size = builder.size;
        this.pages = builder.pages;
        this.total = builder.total;
        this.result = builder.result;
        this.firstPage = builder.firstPage;
        this.lastPage = builder.lastPage;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> implements org.finalframework.core.Builder<Page<T>> {
        private Integer page;
        private Integer size;
        private Integer pages;
        private Long total;
        private List<T> result;
        private Boolean firstPage;
        private Boolean lastPage;

        public Builder<T> page(int page) {
            this.page = page;
            return this;
        }

        public Builder<T> size(int size) {
            this.size = size;
            return this;
        }

        public Builder<T> pages(int pages) {
            this.pages = pages;
            return this;
        }

        public Builder<T> total(long total) {
            this.total = total;
            return this;
        }


        public Builder<T> result(List<T> result) {
            this.result = result;
            return this;
        }

        public Builder<T> firstPage(boolean firstPage) {
            this.firstPage = firstPage;
            return this;
        }

        public Builder<T> lastPage(boolean lastPage) {
            this.lastPage = lastPage;
            return this;
        }

        @Override
        public Page<T> build() {
            return new Page<T>(this);
        }
    }
}
