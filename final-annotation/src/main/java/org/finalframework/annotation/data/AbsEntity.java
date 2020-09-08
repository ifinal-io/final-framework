/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.annotation.data;

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

    


