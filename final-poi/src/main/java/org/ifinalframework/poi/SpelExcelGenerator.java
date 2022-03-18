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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * SpelExcelGenerator.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SpelExcelGenerator extends AbstractExcelGenerator {

    private SpelPropertyAccessor propertyAccessor;

    public SpelExcelGenerator() {
        super();
        this.propertyAccessor = new SpelPropertyAccessor();
    }

    public SpelExcelGenerator(final Workbook workbook) {
        this(workbook, new SpelPropertyAccessor());
    }

    @Override
    protected Object calcCellValue(final Cell rowCell, final Excel.Cell cell, final Object data, final CellType type) {

        StandardEvaluationContext context = new StandardEvaluationContext(data);
        context.addPropertyAccessor(new MapAccessor());
        context.setVariable("cell", rowCell);
        return propertyAccessor.read(cell.getValue(), context);
    }

    public SpelExcelGenerator(final Workbook workbook, final SpelPropertyAccessor propertyAccessor) {
        super(workbook);
        this.propertyAccessor = propertyAccessor;
    }

}
