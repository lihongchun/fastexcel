package cn.idev.excel.test.core.multiplesheets;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.ExcelReader;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.test.util.TestFileUtil;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class MultipleSheetsDataTest {

    private static File file07;
    private static File file03;

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.readFile("multiplesheets" + File.separator + "multiplesheets.xlsx");
        file03 = TestFileUtil.readFile("multiplesheets" + File.separator + "multiplesheets.xls");
    }

    @Test
    public void t01Read07() {
        read(file07);
    }

    @Test
    public void t02Read03() {
        read(file03);
    }

    @Test
    public void t03Read07All() {
        readAll(file07);
    }

    @Test
    public void t04Read03All() {
        readAll(file03);
    }

    private void read(File file) {
        MultipleSheetsListener multipleSheetsListener = new MultipleSheetsListener();
        try (ExcelReader excelReader = EasyExcel.read(file, MultipleSheetsData.class, multipleSheetsListener)
                .build()) {
            List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
            int count = 1;
            for (ReadSheet readSheet : sheets) {
                excelReader.read(readSheet);
                Assertions.assertEquals(multipleSheetsListener.getList().size(), count);
                count++;
            }
        }
    }

    private void readAll(File file) {
        EasyExcel.read(file, MultipleSheetsData.class, new MultipleSheetsListener())
                .doReadAll();
    }
}
