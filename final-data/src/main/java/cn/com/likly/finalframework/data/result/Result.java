package cn.com.likly.finalframework.data.result;

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

    private Integer status;
    private String message;
    private T data;
    private String trace;
    private Long timestamp = System.currentTimeMillis();

    public Result() {
    }

    public Result(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Result(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

}
