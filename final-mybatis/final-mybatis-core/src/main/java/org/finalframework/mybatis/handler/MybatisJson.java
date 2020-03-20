package org.finalframework.mybatis.handler;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import org.finalframework.json.JsonException;
import org.finalframework.json.JsonService;
import org.finalframework.json.TypeReference;
import org.finalframework.json.jackson.FinalJacksonModule;
import org.finalframework.json.jackson.JacksonJsonService;

/**
 * @author sli
 * @version 1.0
 * @date 2020-03-20 23:38:42
 * @since 1.0
 */
public class MybatisJson {

    private static final JsonService jsonService;

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new FinalJacksonModule(objectMapper));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        jsonService = new JacksonJsonService(objectMapper);
    }

    /**
     * 将对象转化为 json 串
     */
    public static String toJson(Object object) {
        try {

            if (object instanceof String) {
                return (String) object;
            }

            return jsonService.toJson(object);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    public static String toJson(Object object, @NonNull Class<?> view) {
        try {
            if (object instanceof String) {
                return (String) object;
            }
            return jsonService.toJson(object, view);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    public static <T> T toObject(@NonNull String json, @NonNull Class<T> classOfT) {
        try {
            return jsonService.toObject(json, classOfT);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    public static <T> T toObject(@NonNull String json, @NonNull Class<T> classOfT, @NonNull Class<?> view) {
        try {
            return jsonService.toObject(json, classOfT, view);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }


    public static <E> List<E> toList(@NonNull String json, @NonNull Class<E> classOfT) {
        return toCollection(json, List.class, classOfT);
    }

    public static <E> List<E> toList(@NonNull String json, @NonNull Class<E> classOfT, @NonNull Class<?> view) {
        return toCollection(json, List.class, classOfT, view);
    }


    public static <E> Set<E> toSet(@NonNull String json, @NonNull Class<E> classOfT) {
        return toCollection(json, Set.class, classOfT);
    }

    public static <E> Set<E> toSet(@NonNull String json, @NonNull Class<E> classOfT, @NonNull Class<?> view) {
        return toCollection(json, Set.class, classOfT, view);
    }

    public static <T> T toObject(@NonNull String json, @NonNull TypeReference<T> typeOfT) {
        return toObject(json, typeOfT.getType());
    }

    public static <T> T toObject(@NonNull String json, @NonNull Type typeOfT) {
        try {
            return jsonService.toObject(json, typeOfT);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    public static <T> T toObject(@NonNull String json, @NonNull TypeReference<T> typeOfT, @NonNull Class<?> view) {
        return toObject(json, typeOfT.getType(), view);
    }

    public static <T> T toObject(@NonNull String json, @NonNull Type typeOfT, @NonNull Class<?> view) {
        try {
            return jsonService.toObject(json, typeOfT, view);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    public static <E, T extends Collection<E>> T toCollection(@NonNull String json, @NonNull Class<T> collectionClass,
        @NonNull Class<E> elementClass) {
        try {
            return jsonService.toCollection(json, collectionClass, elementClass);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }

    public static <E, T extends Collection<E>> T toCollection(@NonNull String json, @NonNull Class<T> collectionClass,
        @NonNull Class<E> elementClass, @NonNull Class<?> view) {
        try {
            return jsonService.toCollection(json, collectionClass, elementClass, view);
        } catch (Throwable e) {
            if (e instanceof JsonException) {
                throw (JsonException) e;
            }
            throw new JsonException(e);
        }
    }
}
