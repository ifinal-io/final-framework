package cn.com.likly.finalframework.data.entity;

import cn.com.likly.finalframework.data.annotation.PrimaryKey;
import cn.com.likly.finalframework.data.entity.enums.YN;
import lombok.Data;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:25
 * @since 1.0
 */
@Data
public class Person implements Entity<Long> {
    @PrimaryKey
    private Long id;
    private List<String> stringList;
    private List<Integer> intList;
    private YN yn = YN.YES;
}
