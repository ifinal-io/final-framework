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

package org.ifinalframework.data.web.servlet.excel;

import org.springframework.stereotype.Component;

import org.ifinalframework.data.domain.excel.ExcelExportService;
import org.ifinalframework.poi.WorkbookWriter;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * HttpServletResponseExcelExportService
 *
 * @author iimik
 * @since 1.5.2
 **/
@Component
public class HttpServletResponseExcelExportService implements ExcelExportService<HttpServletResponse, Void> {
    @Override
    public Void export(String fileName, WorkbookWriter writer, HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName + ".xlsx") + "\"");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");

        writer.write(response.getOutputStream());
        return null;
    }
}
