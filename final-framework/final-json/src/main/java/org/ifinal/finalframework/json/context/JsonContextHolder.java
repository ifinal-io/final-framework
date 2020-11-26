package org.ifinal.finalframework.json.context;


import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class JsonContextHolder {

    private static final ThreadLocal<JsonContext> jsonContextHolder =
            new NamedThreadLocal<>("JsonContext");

    private static final ThreadLocal<JsonContext> inheritableJsonContextHolder =
            new NamedInheritableThreadLocal<>("JsonContext");

    private static JsonContext defaultJson = new SimpleJsonContext();

    public static void resetJsonContext() {
        jsonContextHolder.remove();
    }

    public static void setJsonContext(@Nullable JsonContext jsonContext, boolean inheritable) {
        if (jsonContext == null) {
            resetJsonContext();
        } else {
            if (inheritable) {
                inheritableJsonContextHolder.set(jsonContext);
                jsonContextHolder.remove();
            } else {
                jsonContextHolder.set(jsonContext);
                inheritableJsonContextHolder.remove();
            }
        }
    }

    @Nullable
    public static JsonContext getJsonContext() {
        JsonContext userContext = jsonContextHolder.get();
        if (userContext == null) {
            userContext = inheritableJsonContextHolder.get();
        }
        if (userContext == null) {
            userContext = defaultJson;
        }
        return userContext;
    }

    public static void setJsonContext(@Nullable JsonContext localeContext) {
        setJsonContext(localeContext, false);
    }

    public static void setIgnore(@Nullable boolean ignore, boolean inheritable) {
        JsonContext jsonContext = getJsonContext();
        if (jsonContext == null) {
            jsonContext = new SimpleJsonContext();
        }
        jsonContext.setIgnore(ignore);
        setJsonContext(jsonContext, inheritable);
    }

    public static boolean isIgnore() {
        return Optional.ofNullable(getJsonContext()).orElse(defaultJson).isIgnore();
    }

    public static void setDefaultJson(@Nullable JsonContext jsonContext) {
        JsonContextHolder.defaultJson = jsonContext;
    }


}

