package org.finalframework.data.entity;

import java.time.LocalDateTime;
import org.finalframework.data.annotation.Column;
import org.finalframework.data.annotation.Created;
import org.finalframework.data.annotation.Default;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.annotation.IView;
import org.finalframework.data.annotation.LastModified;
import org.finalframework.data.annotation.PrimaryKey;
import org.finalframework.data.annotation.ReadOnly;
import org.finalframework.data.annotation.Version;
import org.finalframework.data.annotation.View;
import org.finalframework.data.entity.enums.YN;

/**
 * 抽象的基础实现模型，包含数据表记录的基础属性如：{@link #id}
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-20 17:17
 * @since 1.0
 */
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
     * 版本号，在使用 {@link org.finalframework.data.repository.Repository#update} 方法更新时，会插入 {@code version += version}。
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
    @Default
    @Column
    @View(IView.class)
    private YN yn;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public YN getYn() {
        return yn;
    }

    public void setYn(YN yn) {
        this.yn = yn;
    }
}

    


