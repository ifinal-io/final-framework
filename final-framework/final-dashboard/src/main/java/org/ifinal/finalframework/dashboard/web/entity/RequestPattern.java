package org.ifinal.finalframework.dashboard.web.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPattern implements Comparable<RequestPattern> {
    private String name;
    private Set<RequestMethod> methods;
    private String pattern;

    @Override
    public int compareTo(final RequestPattern o) {

        return this.pattern.compareTo(o.pattern);
    }
}

