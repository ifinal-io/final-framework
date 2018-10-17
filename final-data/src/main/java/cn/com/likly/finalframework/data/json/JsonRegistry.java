package cn.com.likly.finalframework.data.json;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:27
 * @since 1.0
 */
public class JsonRegistry {
    private static final JsonRegistry ourInstance = new JsonRegistry();
    private JsonService jsonService;

    private JsonRegistry() {
    }

    public static JsonRegistry getInstance() {
        return ourInstance;
    }

    public JsonService getJsonService() {
        if (jsonService == null) {
            throw new NullPointerException("json service had not been registered.");
        }
        return jsonService;
    }

    public void setJsonService(JsonService jsonService) {
        this.jsonService = jsonService;
    }
}
