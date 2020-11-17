package org.finalframework.dashboard.core.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-15 09:32:22
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPattern implements Comparable<RequestPattern> {
    @Nullable
    private String name;
    @NonNull
    private RequestMethod method;
    @NonNull
    private String pattern;

    @Override
    public int compareTo(RequestPattern o) {
        return this.pattern.compareTo(o.pattern);
    }
}

