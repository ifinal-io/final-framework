package org.ifinal.finalframework.json.context;

import java.util.Optional;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class JsonContextHolder {

    private static final ThreadLocal<JsonContext> JSON_CONTEXT =
        new NamedThreadLocal<>("JsonContext");

    private static final ThreadLocal<JsonContext> INHERITABLE_JSON_CONTEXT =
        new NamedInheritableThreadLocal<>("JsonContext");

    private static JsonContext defaultJson = new SimpleJsonContext();

    private JsonContextHolder() {
    }

    public static void resetJsonContext() {
        JSON_CONTEXT.remove();
    }

    public static void setJsonContext(final @Nullable JsonContext jsonContext, final boolean inheritable) {

        if (jsonContext == null) {
            resetJsonContext();
        } else {
            if (inheritable) {
                INHERITABLE_JSON_CONTEXT.set(jsonContext);
                JSON_CONTEXT.remove();
            } else {
                JSON_CONTEXT.set(jsonContext);
                INHERITABLE_JSON_CONTEXT.remove();
            }
        }
    }

    public static void setJsonContext(final @Nullable JsonContext localeContext) {
        setJsonContext(localeContext, false);
    }

    @Nullable
    public static JsonContext getJsonContext() {
        JsonContext userContext = JSON_CONTEXT.get();
        if (userContext == null) {
            userContext = INHERITABLE_JSON_CONTEXT.get();
        }
        if (userContext == null) {
            userContext = defaultJson;
        }
        return userContext;
    }

    public static void setIgnore(final boolean ignore, final boolean inheritable) {

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

    public static void setDefaultJson(final @Nullable JsonContext jsonContext) {

        JsonContextHolder.defaultJson = jsonContext;
    }

}

