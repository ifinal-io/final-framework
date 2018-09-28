package cn.com.likly.finalframework.context.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:08
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -5373427854167752891L;

    private Integer status;
    private String message;
    private T data;
    private String trace;
    private Long timestamp;
}
