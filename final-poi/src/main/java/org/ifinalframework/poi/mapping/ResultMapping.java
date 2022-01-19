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

package org.ifinalframework.poi.mapping;

import lombok.*;
import org.ifinalframework.poi.databind.ExcelDeserializer;
import org.ifinalframework.poi.databind.ExcelSerializer;

/**
 * @author likly
 * @version 1.2.4
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultMapping {
    private Integer index;
    private String property;
    private String column;
    private ExcelSerializer serializer;
    private ExcelDeserializer deserializer;
}
