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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * WorkbookWriter.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface WorkbookWriter {

    WorkbookWriter append(int sheetIndex, List<?> rows);

    default WorkbookWriter append(List<?> rows) {
        return append(0, rows);
    }

    void write(OutputStream os) throws IOException;

    default void write(String filename) throws IOException {
        write(new FileOutputStream(filename));
    }

}
