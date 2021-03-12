package org.ifinal.finalframework.data.poi.excel;

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * SpelExcelGenerator.
 *
 * @author likly
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
