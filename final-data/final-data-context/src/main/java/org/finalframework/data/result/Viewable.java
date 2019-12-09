package org.finalframework.data.result;

import com.fasterxml.jackson.annotation.JsonView;

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

    Class<?> getView();

}
