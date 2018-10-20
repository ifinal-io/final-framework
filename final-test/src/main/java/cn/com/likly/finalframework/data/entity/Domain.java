package cn.com.likly.finalframework.data.entity;

import cn.com.likly.finalframework.data.annotation.PrimaryKey;
import lombok.Data;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 20:28
 * @since 1.0
 */
@Data
public class Domain implements Entity<Long> {
    @PrimaryKey
    private Long id;
}
