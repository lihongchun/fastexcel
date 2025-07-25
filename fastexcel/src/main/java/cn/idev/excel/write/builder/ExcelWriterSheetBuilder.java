package cn.idev.excel.write.builder;

import cn.idev.excel.ExcelWriter;
import cn.idev.excel.exception.ExcelGenerateException;
import cn.idev.excel.write.metadata.WriteSheet;
import cn.idev.excel.write.metadata.fill.FillConfig;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Build sheet
 *
 *
 */
public class ExcelWriterSheetBuilder extends AbstractExcelWriterParameterBuilder<ExcelWriterSheetBuilder, WriteSheet> {
    private ExcelWriter excelWriter;
    /**
     * Sheet
     */
    private final WriteSheet writeSheet;

    public ExcelWriterSheetBuilder() {
        this.writeSheet = new WriteSheet();
    }

    public ExcelWriterSheetBuilder(ExcelWriter excelWriter) {
        this.writeSheet = new WriteSheet();
        this.excelWriter = excelWriter;
    }

    /**
     * Starting from 0
     *
     * @param sheetNo
     * @return
     */
    public ExcelWriterSheetBuilder sheetNo(Integer sheetNo) {
        writeSheet.setSheetNo(sheetNo);
        return this;
    }

    /**
     * sheet name
     *
     * @param sheetName
     * @return
     */
    public ExcelWriterSheetBuilder sheetName(String sheetName) {
        writeSheet.setSheetName(sheetName);
        return this;
    }

    public WriteSheet build() {
        return writeSheet;
    }

    public void doWrite(Collection<?> data) {
        if (excelWriter == null) {
            throw new ExcelGenerateException("Must use 'FastExcelFactory.write().sheet()' to call this method");
        }
        excelWriter.write(data, build());
        excelWriter.finish();
    }

    public void doFill(Object data) {
        doFill(data, null);
    }

    public void doFill(Object data, FillConfig fillConfig) {
        if (excelWriter == null) {
            throw new ExcelGenerateException("Must use 'FastExcelFactory.write().sheet()' to call this method");
        }
        excelWriter.fill(data, fillConfig, build());
        excelWriter.finish();
    }

    public void doWrite(Supplier<Collection<?>> supplier) {
        doWrite(supplier.get());
    }

    public void doFill(Supplier<Object> supplier) {
        doFill(supplier.get());
    }

    public void doFill(Supplier<Object> supplier, FillConfig fillConfig) {
        doFill(supplier.get(), fillConfig);
    }

    public ExcelWriterTableBuilder table() {
        return table(null);
    }

    public ExcelWriterTableBuilder table(Integer tableNo) {
        ExcelWriterTableBuilder excelWriterTableBuilder = new ExcelWriterTableBuilder(excelWriter, build());
        if (tableNo != null) {
            excelWriterTableBuilder.tableNo(tableNo);
        }
        return excelWriterTableBuilder;
    }

    @Override
    protected WriteSheet parameter() {
        return writeSheet;
    }
}
