package org.ifinal.finalframework.dashboard.context.service.query;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import lombok.Data;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class BeanQuery implements Serializable {

    Class<? extends Annotation> annotation;

    private Boolean springFramework = false;

    private Boolean finalFramework = false;

}
