package cn.com.likly.finalframework.mybatis.holder;

import cn.com.likly.finalframework.mybatis.annotation.enums.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 22:03
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class TableHolder {
    private final String table;
    private final String alias;
    private final PrimaryKey primaryKey;
}
