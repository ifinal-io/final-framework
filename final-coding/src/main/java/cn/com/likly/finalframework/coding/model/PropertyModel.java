package cn.com.likly.finalframework.coding.model;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-23 10:17
 * @since 1.0
 */
public class PropertyModel implements Serializable {
    private String name;
    private String type;
    private boolean idProperty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIdProperty() {
        return idProperty;
    }

    public void setIdProperty(boolean idProperty) {
        this.idProperty = idProperty;
    }


}
