package org.finalframework.spring.annotation.factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 11:36:11
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.SOURCE)
@SpringFactory(value = RestController.class, expand = true)
@SpringFactory(value = Controller.class, expand = true)
public @interface SpringController {
}
