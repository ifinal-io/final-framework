package org.finalframework.json.gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.finalframework.json.JsonService;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-02 11:22
 * @since 1.0
 */
@Slf4j
public class GsonJsonService implements JsonService {

    @Setter
    private Gson gson;

    public GsonJsonService(Gson gson) {
        this.gson = gson;
    }

    public GsonJsonService() {
        this(new Gson());
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T toObject(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    @Override
    public <T> T toObject(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E, T extends Collection<E>> T toCollection(String json, Class<T> collectionClass, Class<E> elementClass) throws Throwable {

        JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();

        Collection<E> result = List.class.equals(collectionClass) ? new ArrayList<>()
                : Set.class.equals(collectionClass) ? new HashSet<>() : null;
        if (result == null) throw new UnsupportedOperationException("");

        for (JsonElement element : jsonArray) {
            result.add(gson.fromJson(element, elementClass));
        }
        return (T) result;

    }

}
