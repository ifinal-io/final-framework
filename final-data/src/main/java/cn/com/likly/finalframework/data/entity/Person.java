package cn.com.likly.finalframework.data.entity;

import cn.com.likly.finalframework.data.annotation.NonColumn;
import cn.com.likly.finalframework.data.annotation.PrimaryKey;
import cn.com.likly.finalframework.data.mapping.holder.EntityHolder;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-16 16:19
 * @since 1.0
 */
@Slf4j
@Data
public class Person implements Entity<Long> {
    @PrimaryKey
    private Long id;
    @NonColumn
    private String name;

    public static void main(String[] args) {

        EntityHolder<?, ? extends PropertyHolder> entityHolder = EntityHolder.from(Person.class);
        logger.info("entity={},table={}", entityHolder.getName(), entityHolder.getTable());
        entityHolder.stream()
                .forEach(it -> {
                    logger.info(
                            "property={},column={},id={},unique={},nullable={},insertable={},updatable={},transient={}",
                            it.getName(),
                            it.getColumn(),
                            it.isIdProperty(),
                            ((PropertyHolder) it).unique(),
                            ((PropertyHolder) it).nullable(),
                            ((PropertyHolder) it).insertable(),
                            ((PropertyHolder) it).updatable(),
                            ((PropertyHolder) it).isTransient()
                    );
                });

        System.out.println();
    }
}
