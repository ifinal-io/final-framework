package cn.com.likly.finalframework.mybatis.holder;

import cn.com.likly.finalframework.context.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 22:09
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class EntityHolder {
    private final Class<? extends Entity> entityClass;
    private final TableHolder table;
    private final PropertyHolder primaryKey;
    private final List<PropertyHolder> properties;
}
