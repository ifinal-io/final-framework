package org.finalframework.json;


import org.finalframework.core.Assert;

/**
 * Json service register.
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:27
 * @since 1.0
 */
public final class JsonRegistry {
    private static final JsonRegistry instance = new JsonRegistry();
    private static final String DEFAULT_JSON_SERVICE = "org.finalframework.json.jackson.JacksonJsonService";
    private JsonService jsonService;
    private boolean initDefaulted = false;

    private JsonRegistry() {
    }

    public static JsonRegistry getInstance() {
        return instance;
    }

    /**
     * return the {@link JsonService} registered by {@link #register(JsonService)}
     */
    public JsonService getJsonService() {
        if (jsonService == null && !initDefaulted) {
            initDefaultJsonService();
        }
        Assert.isNull(jsonService, "json service is not registered!!!");
        return jsonService;
    }

    private synchronized void initDefaultJsonService() {

        try {
            this.jsonService = (JsonService) Class.forName(DEFAULT_JSON_SERVICE).newInstance();
        } catch (Exception e) {
            // ignore
        } finally {
            initDefaulted = true;
        }

    }

    public synchronized void register(JsonService jsonService) {
        Assert.isNull(jsonService, "json service can not be null");
        this.jsonService = jsonService;
    }
}
