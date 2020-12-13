package org.ifinal.finalframework.dashboard.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class Page implements Serializable {

    private String title;

    private List<String> menus;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {

        this.title = title;
    }

    public List<String> getMenus() {
        return menus;
    }

    public void setMenus(final List<String> menus) {

        this.menus = menus;
    }

}

