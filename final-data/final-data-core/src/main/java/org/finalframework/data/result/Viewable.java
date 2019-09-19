package org.finalframework.data.result;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-06 15:50:41
 * @see JsonView
 * @since 1.0
 */
public interface Viewable {

    Class<?> getView();

    void setView(Class<?> view);

}
