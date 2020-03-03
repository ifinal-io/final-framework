package org.finalframework.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.finalframework.data.annotation.*;
import org.finalframework.data.entity.enums.YN;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 抽象的基础实现模型，包含数据表记录的基础属性如：{@link #id}
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-20 17:17
 * @since 1.0
 */
@Setter
@Getter
public abstract class AbsEntity implements IEntity<Long> {

    private static final long serialVersionUID = -3500516904657883963L;

    /**
     * 主键，流水号
     */
    @PrimaryKey
    private Long id;
    /**
     * 版本号，在使用 {@link org.finalframework.data.repository.Repository#update} 方法更新时，会插入 {@code version += version}。
     */
    @Version
    private Long version;
    /**
     * 创建时间
     */
    @Created
    private LocalDateTime created;
    /**
     * 最后修改时间
     */
    @LastModified
    private LocalDateTime lastModified;
    /**
     * 有效标记
     */
    @Column(insertable = false)
    private YN yn;
}

    


