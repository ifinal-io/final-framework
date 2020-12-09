package org.ifinal.finalframework.data.annotation;

import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.core.annotation.IEntity;
import org.ifinal.finalframework.core.annotation.IView;

import java.time.LocalDateTime;

/**
 * The build-in base impl of {@linkplain IEntity entity} which have the common property.
 * such as {@link #id}, {@link #version},{@link #created},{@link #lastModified},{@link #yn}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
@Transient
public class AbsEntity implements IEntity<Long> {

    private static final long serialVersionUID = -3500516904657883963L;

    /**
     * 主键，流水号
     */
    @AutoInc
    @PrimaryKey
    @View(IView.class)
    private Long id;
    /**
     * 版本号
     */
    @Version
    @View(IView.class)
    private Long version;
    /**
     * 创建时间
     */
    @Created
    @View(IView.class)
    private LocalDateTime created;
    /**
     * 最后修改时间
     */
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

    


