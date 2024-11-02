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

package org.ifinalframework.core.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.ifinalframework.core.IData;
import org.ifinalframework.core.IException;
import org.ifinalframework.core.IResult;
import org.ifinalframework.core.IUser;
import org.ifinalframework.core.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务数据返回结果封装，统一业务返回的数据结构。
 *
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public final class Result<T> implements IResult<T>, Responsible, Serializable {

    @Serial
    private static final long serialVersionUID = -2801752781032532754L;

    /**
     * the result {@literal status}
     *
     * @see ResponseStatus#getCode()
     */
    private Integer status;

    /**
     * the result {@literal description}
     *
     * @see ResponseStatus#getDesc()
     */
    private String description;

    /**
     * the result error {@linkplain IException#getCode() code}
     */
    private String code;

    /**
     * the result error {@linkplain IException#getMessage() message}
     */
    private String message;

    /**
     * the header info of {@link #data}.
     *
     * @see Column
     * @since 1.2.0
     */
    private List<Column> header;

    /**
     * the result {@linkplain IData#getData() data}
     */
    @SuppressWarnings("all")
    private T data;

    /**
     * the {@linkplain Pagination pagination}
     */
    private Pagination pagination;

    /**
     * trace
     */
    private String trace;

    /**
     * 响应时间戳
     */
    private Long timestamp = System.currentTimeMillis();

    /**
     * 执行时长
     */
    private Duration duration;

    /**
     * 服务地址
     */
    private String address;

    /**
     * 服务IP
     */
    private String ip;

    /**
     * 国际化
     */
    private Locale locale;

    /**
     * 时区
     */
    private TimeZone timeZone;

    /**
     * 操作者
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private IUser<?> operator;

    /**
     * @since 1.5.6
     */
    private String version;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Class<?> view;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Class<? extends Throwable> exception;

    /**
     * 应用名称，一般取自 {@code spring.application.name}变量。
     *
     * @since 1.6.0
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String application;

    public Result() {
    }

    public Result(final Integer status, final String description, final String code, final String message,
                  final T data) {
        this.status = Objects.requireNonNull(status);
        this.description = Objects.requireNonNull(description);
        this.code = Objects.requireNonNull(code);
        this.message = Objects.requireNonNull(message);
        this.data = data;
    }

    public Result(final Integer status, final String description, final String code, final String message) {
        this(status, description, code, message, null);
    }

    public Result(final Integer status, final String description, final T data) {
        this(status, description, status.toString(), description, data);
    }

    public Result(final Integer status, final String description) {
        this(status, description, status.toString(), description);
    }

}
