

package org.finalframework.json.jackson.view;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 可视图的，实现根据业务场景而展示不同的视图，主要结合{@link JsonView}使用。
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-06 15:50:41
 * @see JsonView
 * @since 1.0
 */
public interface Viewable<T> {

    /**
     * return the {@linkplain Class view} of {@link JsonView#value()}
     *
     * @return the view
     */
    @NonNull
    Class<?> getView();

    /**
     * return the value of this view.
     *
     * @return the value.
     */
    @Nullable
    T getValue();

}
