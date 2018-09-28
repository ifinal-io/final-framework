package cn.com.likly.finalframework.context.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:09
 * @since 1.0
 */
@Data
public class PageResult<T> implements Serializable {

    private Integer page;
    private Integer size;
    private Integer pages;
    private Long total;
    private List<T> result;
    private Boolean firstPage;
    private Boolean lastPage;
}
