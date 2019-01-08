package com.ilikly.finalframework.data.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 22:57
 * @since 1.0
 */
@Data
public class JsonBean {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    private Integer age;
}
