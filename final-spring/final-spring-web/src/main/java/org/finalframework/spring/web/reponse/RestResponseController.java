package org.finalframework.spring.web.reponse;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-14 14:57:22
 * @since 1.0
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
