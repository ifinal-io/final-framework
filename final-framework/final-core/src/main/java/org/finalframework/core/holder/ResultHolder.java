package org.finalframework.core.holder;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-08 20:09:57
 * @since 1.0
 */
public interface ResultHolder<PARAM, RESULT, HOLDER> {

    HOLDER buildHolder(PARAM param);

    RESULT parseHolder(HOLDER holder);


}