package org.ifinal.finalframework.web.response.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0.0
 * @see RestController
 * @see ResponseBody
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface RestResponseController {
    @AliasFor(annotation = Controller.class)
    String value() default "";
}
