package org.finalframework.data.result;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:08
 * @since 1.0
 */
@Data
public final class Result<T> implements Serializable {

    private static final long serialVersionUID = -5373427854167752891L;

    @JsonView(View.class)
    private Integer status;
    @JsonView(View.class)
    private String message;
    @JsonView
    private String toast;
    @JsonView(View.class)
    private T data;
    @JsonView(View.class)
    private String trace;
    @JsonView(View.class)
    private Long timestamp = System.currentTimeMillis();

    public interface View {
    }

    public Result() {
    }

    public Result(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Result(Integer status, String message, String toast) {
        this.status = status;
        this.message = message;
        this.toast = toast;
    }

    public Result(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isSuccess() {
        return status == null || status == 0;
    }

}
