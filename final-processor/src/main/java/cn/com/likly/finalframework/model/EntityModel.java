package cn.com.likly.finalframework.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 20:38
 * @since 1.0
 */
@Data
@Builder
public class EntityModel {
    private String packageName;
    private String entityName;
    private String importClass;
    private Set<String> properties;
}
