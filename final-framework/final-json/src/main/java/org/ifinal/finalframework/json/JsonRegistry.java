package org.ifinal.finalframework.json;


import org.ifinal.finalframework.util.Asserts;

/**
 * Json service register.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class JsonRegistry {

    private static final JsonRegistry instance = new JsonRegistry();
    private static final String DEFAULT_JSON_SERVICE = "org.ifinal.finalframework.json.jackson.JacksonJsonService";
    private JsonService jsonService;
    private boolean initDefaulted = false;

    private JsonRegistry() {
    }

    public static JsonRegistry getInstance() {
        return instance;
    }

    /**
     * @return the {@link JsonService} registered by {@link #register(JsonService)}
     */
    public JsonService getJsonService() {
        if (jsonService == null && !initDefaulted) {
            initDefaultJsonService();
        }
        Asserts.isNull(jsonService, "json service is not registered!!!");
        return jsonService;
    }

    private synchronized void initDefaultJsonService() {

        try {
            this.jsonService = (JsonService) Class.forName(DEFAULT_JSON_SERVICE).getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        } finally {
            initDefaulted = true;
        }

    }

    public synchronized void register(JsonService jsonService) {
        Asserts.isNull(jsonService, "json service can not be null");
        this.jsonService = jsonService;
    }
}
