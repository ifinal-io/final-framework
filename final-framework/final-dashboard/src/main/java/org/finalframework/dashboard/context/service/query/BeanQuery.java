package org.finalframework.dashboard.context.service.query;

import lombok.Data;

import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 20:15:30
 * @since 1.0
 */
@Data
public class BeanQuery implements Serializable {

    private Boolean springFramework = false;
    private Boolean finalFramework = false;
    Class<? extends Annotation> annotation;

}
