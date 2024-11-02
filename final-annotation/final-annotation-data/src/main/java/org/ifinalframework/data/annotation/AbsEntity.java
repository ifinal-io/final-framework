/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.annotation;

import java.time.LocalDateTime;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IView;
import org.ifinalframework.core.lang.Default;
import org.ifinalframework.core.lang.Transient;

import lombok.Getter;
import lombok.Setter;

/**
 * The built-in base impl of {@linkplain IEntity entity} which have the common property. such as {@link #id}, {@link
 * #version},{@link #created},{@link #lastModified},{@link #yn}.
 *
 * <pre class="code">
 * CREATE TABLE tableName
 * (
 *     id            BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '流水号',
 *     version       INT(11)    NOT NULL DEFAULT 1 COMMENT '版本号',
 *     created       DATETIME   NOT NULL DEFAULT NOW() COMMENT '创建时间',
 *     last_modified DATETIME   NULL     DEFAULT NULL ON UPDATE NOW() COMMENT '最后修改时间',
 *     yn            INT(11)    NOT NULL DEFAULT 1 COMMENT '有效标记，1：有效，0：无效',
 *     PRIMARY KEY (id)
 * )
 * </pre>
 *
 * @author iimik
 * @version 1.0.0
 * @see Version
 * @see Created
 * @see LastModified
 * @see YN
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
     * 版本号，更新时自增，version = version + 1
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
     * 最后修改时间，使用数据库 ON UPDATE 操作
     * <pre class="code">
     * UPDATE table SET version = version + 1;
     * </pre>
     */
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

}

    


