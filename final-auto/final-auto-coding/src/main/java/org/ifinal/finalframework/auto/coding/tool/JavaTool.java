package org.ifinal.finalframework.auto.coding.tool;


import org.apache.velocity.tools.config.DefaultKey;
import org.springframework.lang.NonNull;

import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@DefaultKey("java")
public class JavaTool {

    public static String name(@NonNull TypeElement element) {
        return element.getQualifiedName().toString().replaceAll("java.lang", "");
    }

    public static String simpleName(@NonNull TypeElement element) {
        return element.getSimpleName().toString();
    }

}

