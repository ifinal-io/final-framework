/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.core;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 可审核对象接口。
 *
 * @author iimik
 * @version 1.5.1
 * @since 1.5.1
 */
public interface IAudit<T extends IUser<?>> {
    /**
     * 设置审核状态
     *
     * @param status 审核状态
     */
    void setAuditStatus(AuditStatus status);

    /**
     * 获取审核状态
     *
     * @return 审核状态
     */
    AuditStatus getAuditStatus();

    /**
     * 设置审核内容
     *
     * @param auditContent 审核内容
     */
    void setAuditContent(String auditContent);

    /**
     * 获取审核内容
     *
     * @return 审核内容
     */
    String getAuditContent();

    /**
     * 设置审核时间
     *
     * @param auditDateTime 审核时间
     */
    void setAuditDateTime(LocalDateTime auditDateTime);

    /**
     * 获取审核时间
     *
     * @return 审核时间
     */
    LocalDateTime getAuditDateTime();

    /**
     * 设置审核人
     *
     * @param auditor 审核人
     */
    void setAuditor(T auditor);

    /**
     * 获取审核人
     *
     * @return 审核人
     */
    T getAuditor();

    @Getter
    @RequiredArgsConstructor
    enum AuditStatus implements IEnum<Integer> {
        NONE(0, "无"),
        PENDING(10, "待审核"),
        CANCELED(21, "已取消"),
        AUDITED(22, "已审核"),
        REJECTED(23, "驳回"),
        ;
        private final Integer code;
        private final String desc;

    }

}
