

package org.finalframework.json;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 统一的Json调用入口 为常用的Json序列化与反序列化提供统一的入口。
 * <ul>
 * <li>序列化 {@link #toJson(Object)} 实现将任何对象序列化为Json字符串。</li>
 * <li>反序列化 {@link #toObject(String, Class)} (String, Class)}和{@link #toList(String, Class)} (String, Class, Class)}分别实现将Json字符串反序列化为{@link Object}和{@link Collection<Object>}</li>
 * </ul>
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:29
 * @see JsonService
 * @see JsonRegistry
 * @since 1.0
 */
public interface Json {

    /**
     * return the {@linkplain Object value} json string
     *
     * @param object the value
     * @return the json string
     * @throws JsonException json exception
     */
    static String toJson(@Nullable Object object) {
        return toJson(object, null);
    }

    /**
     * return json string of {@linkplain Object object} with the {@linkplain Class view}.
     *
     * @param object the object
     * @param view   the view
     * @return json string
     * @throws JsonException json exception
     */
    static String toJson(@Nullable Object object, @Nullable Class<?> view) {
        try {
            if (object instanceof String) {
                return (String) object;
            }
            return JsonRegistry.getInstance().getJsonService().toJson(object, view);
        } catch (JsonException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }


    /**
     * return json value of {@linkplain String json}
     *
     * @param json json string
     * @return json value
     * @throws JsonException json exception
     */
    static Object toObject(@Nullable String json) {
        return toObject(json, Object.class);
    }

    /**
     * return the json value of {@linkplain String json}
     *
     * @param json     json string
     * @param classOfT json value type
     * @param <T>      json type
     * @return json value
     */
    static <T> T toObject(@Nullable String json, @NonNull Class<T> classOfT) {
        return toObject(json, classOfT, null);
    }

    /**
     * return json value of {@linkplain String json}
     *
     * @param json     json string
     * @param classOfT json value type
     * @param view     json view
     * @param <T>      json type
     * @return json value
     * @throws JsonException json exception
     */
    static <T> T toObject(@Nullable String json, @NonNull Class<T> classOfT, @Nullable Class<?> view) {
        try {
            return JsonRegistry.getInstance().getJsonService().toObject(json, classOfT, view);
        } catch (JsonException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }


    /**
     * return json {@linkplain List list} of {@linkplain String json}.
     *
     * @param json     json string
     * @param classOfT json element value type
     * @param <E>      json element type
     * @return json list
     * @throws JsonException json exception
     */
    static <E> List<E> toList(@Nullable String json, @NonNull Class<E> classOfT) {
        return toList(json, classOfT, null);
    }

    /**
     * return json {@linkplain List list} of {@linkplain String json}.
     *
     * @param json     json string
     * @param classOfT json element value type
     * @param view     json view
     * @param <E>      json element type
     * @return json list
     * @throws JsonException json exception
     */
    static <E> List<E> toList(@Nullable String json, @NonNull Class<E> classOfT, @Nullable Class<?> view) {
        try {
            return JsonRegistry.getInstance().getJsonService().toList(json, classOfT, view);
        } catch (JsonException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }


    /**
     * return json {@linkplain Set set} of json {@linkplain String json}
     *
     * @param json     json string
     * @param classOfT json element value type
     * @param <E>      json element type
     * @return json set
     * @throws JsonException json exception
     */
    static <E> Set<E> toSet(@Nullable String json, @NonNull Class<E> classOfT) {
        return toSet(json, classOfT, null);
    }

    /**
     * return json {@linkplain Set set} of json {@linkplain String json}
     *
     * @param json     json string
     * @param classOfT json element value type
     * @param view     json view
     * @param <E>      json element type
     * @return json set
     * @throws JsonException json exception
     */
    static <E> Set<E> toSet(@Nullable String json, @NonNull Class<E> classOfT, @Nullable Class<?> view) {
        try {
            return JsonRegistry.getInstance().getJsonService().toSet(json, classOfT, view);
        } catch (JsonException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    /**
     * return json {@linkplain Object value} of json {@linkplain String json}.
     *
     * @param json    json string
     * @param typeOfT json value type
     * @param <T>     json type
     * @return json value
     * @throws JsonException json exception
     */
    static <T> T toObject(@NonNull String json, @NonNull TypeReference<T> typeOfT) {
        return toObject(json, typeOfT.getType());
    }

    /**
     * return json {@linkplain Object value} of json {@linkplain String json}.
     *
     * @param json    json string
     * @param typeOfT json value type
     * @param <T>     json type
     * @return json value
     * @throws JsonException json exception
     */
    static <T> T toObject(@NonNull String json, @NonNull Type typeOfT) {
        try {
            return JsonRegistry.getInstance().getJsonService().toObject(json, typeOfT);
        } catch (JsonException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    /**
     * return json {@linkplain Object value} of json {@linkplain String json}.
     *
     * @param json    json string
     * @param typeOfT json value type
     * @param view    json view
     * @param <T>     json type
     * @return json value
     * @throws JsonException json exception
     */
    static <T> T toObject(@NonNull String json, @NonNull TypeReference<T> typeOfT, @Nullable Class<?> view) {
        return toObject(json, typeOfT.getType(), view);
    }

    /**
     * return json {@linkplain Object value} of json {@linkplain String json}.
     *
     * @param json    json string
     * @param typeOfT json value type
     * @param view    json view
     * @param <T>     json type
     * @return json value
     * @throws JsonException json exception
     */
    static <T> T toObject(@NonNull String json, @NonNull Type typeOfT, @Nullable Class<?> view) {
        try {
            return JsonRegistry.getInstance().getJsonService().toObject(json, typeOfT, view);
        } catch (JsonException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }
}
