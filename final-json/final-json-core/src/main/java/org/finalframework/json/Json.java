package org.finalframework.json;

import lombok.NonNull;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * 统一的Json调用入口
 * 为常用的Json序列化与反序列化提供统一的入口。
 * <ul>
 * <li>序列化 {@link #toJson(Object)} 实现将任何对象序列化为Json字符串。</li>
 * <li>反序列化 {@link #parse(String, Class)}和{@link #parse(String, Class, Class)}分别实现将Json字符串反序列化为{@link Object}和{@link Collection< Object>}</li>
 * </ul>
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:29
 * @see JsonService
 * @see JsonRegistry
 * @since 1.0
 */
@SuppressWarnings("all")
public interface Json {

    /**
     * 将对象转化为 json 串
     *
     * @param object
     * @return
     */
    static String toJson(Object object) {
        try {

            if(object instanceof String){
                return (String) object;
            }

            return JsonRegistry.getInstance().getJsonService().toJson(object);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    static String toJson(Object object, @NonNull Class<?> view) {
        try {
            if (object instanceof String) {
                return (String) object;
            }
            return JsonRegistry.getInstance().getJsonService().toJson(object, view);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    static <T> T parse(@NonNull String json, @NonNull Class<T> classOfT) {
        try {
            return JsonRegistry.getInstance().getJsonService().parse(json, classOfT);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    static <T> T parseWithView(@NonNull String json, @NonNull Class<T> classOfT, @NonNull Class<?> view) {
        try {
            return JsonRegistry.getInstance().getJsonService().parse(json, classOfT);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    static <T> T parse(@NonNull String json, @NonNull TypeReference<T> typeOfT) {
        return parse(json, typeOfT.getType());
    }

    static <T> T parse(@NonNull String json, @NonNull Type typeOfT) {
        try {
            return JsonRegistry.getInstance().getJsonService().parse(json, typeOfT);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    static <T> T parse(@NonNull String json, @NonNull Type typeOfT, @NonNull Class<?> view) {
        try {
            return JsonRegistry.getInstance().getJsonService().parseWithView(json, typeOfT, view);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    static <E, T extends Collection<E>> T parse(@NonNull String json, @NonNull Class<T> collectionClass, @NonNull Class<E> elementClass) {
        try {
            return JsonRegistry.getInstance().getJsonService().parse(json, collectionClass, elementClass);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    static <E, T extends Collection<E>> T parse(@NonNull String json, @NonNull Class<T> collectionClass, @NonNull Class<E> elementClass, @NonNull Class<?> view) {
        try {
            return JsonRegistry.getInstance().getJsonService().parseWithView(json, collectionClass, elementClass, view);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

}
