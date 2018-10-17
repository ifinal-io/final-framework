package cn.com.likly.finalframework.data.exception;

import lombok.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 20:57
 * @since 1.0
 */
public interface IException {

    @NonNull
    Integer getCode();

    @NonNull
    String getMessage();
}
