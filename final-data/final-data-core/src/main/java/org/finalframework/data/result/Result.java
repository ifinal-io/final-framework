package org.finalframework.data.result;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.finalframework.data.result.jackson.serializer.ResultDataSerializer;

import java.io.Serializable;

/**
 * 业务数据返回结果封装，统一业务返回的数据结构。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:08
 * @see Page
 * @since 1.0
 */
@Data
public final class Result<T> implements Serializable {

    private static final long serialVersionUID = -5373427854167752891L;
    /**
     * 成功的状态码
     */
    private static final Integer SUCCESS_STATUS = 0;
    /**
     * 状态码
     */
    @JsonView(View.class)
    private Integer status;
    /**
     * 状态描述
     */
    @JsonView(View.class)
    private String message;
    /**
     * 提示消息
     */
    @JsonView
    private String toast;
    /**
     * 业务数据
     */
    @JsonSerialize(using = ResultDataSerializer.class)
    @JsonView(View.class)
    private T data;
    /**
     * trace
     */
    @JsonView(View.class)
    private String trace;
    /**
     * 响应时间戳
     */
    @JsonView(View.class)
    private Long timestamp = System.currentTimeMillis();
    /**
     * 执行时长
     */
    @JsonView(View.class)
    private Long duration;

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
