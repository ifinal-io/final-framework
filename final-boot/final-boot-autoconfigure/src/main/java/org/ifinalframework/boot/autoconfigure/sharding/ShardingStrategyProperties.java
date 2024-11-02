/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.boot.autoconfigure.sharding;

import java.io.Serializable;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 分片策略
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
public class ShardingStrategyProperties implements Serializable {

    private static final long serialVersionUID = 6419098165263663657L;

    private String type;

    private String shardingColumn;

    private Properties props;

}

