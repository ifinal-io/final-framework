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

package org.ifinalframework.poi;

/**
 * SpelExcelWriter.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SpelExcelWriter extends AbstractExcelWriter {

    public SpelExcelWriter(Excel excel) {
        this(excel, null);
    }

    public SpelExcelWriter(final Excel excel, Object data) {
        super(excel, data, new SpelExcelGenerator());
    }

}
