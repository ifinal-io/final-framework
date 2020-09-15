

package org.finalframework.annotation.data;

import lombok.Data;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IView;

import java.time.LocalDateTime;

/**
 * 抽象的基础实现模型，包含数据表记录的基础属性如：{@link #id}
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-20 17:17
 * @since 1.0
 */
@Transient
@Data
public class AbsEntity implements IEntity<Long> {

    private static final long serialVersionUID = -3500516904657883963L;

    /**
     * 主键，流水号
     */
    @Default
    @PrimaryKey
    @View(IView.class)
    private Long id;
    /**
     * 版本号
     */
    @Default
    @ReadOnly
    @Version
    @View(IView.class)
    private Long version;
    /**
     * 创建时间
     */
    @Default
    @Created
    @View(IView.class)
    private LocalDateTime created;
    /**
     * 最后修改时间
     */
    @ReadOnly
    @LastModified
    @View(IView.class)
    private LocalDateTime lastModified;
    /**
     * 有效标记
     */
    @Order(Integer.MAX_VALUE)
    @Default
    @Column
    @View(IView.class)
    private YN yn;


}

    


