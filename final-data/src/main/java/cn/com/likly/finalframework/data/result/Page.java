package cn.com.likly.finalframework.data.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:09
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> implements Serializable {

    private Integer page;
    private Integer size;
    private Integer pages;
    private Long total;
    private List<T> result;
    private Boolean firstPage;
    private Boolean lastPage;
}
