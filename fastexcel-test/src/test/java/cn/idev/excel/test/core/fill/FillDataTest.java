package cn.idev.excel.test.core.fill;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.ExcelWriter;
import cn.idev.excel.enums.WriteDirectionEnum;
import cn.idev.excel.exception.ExcelGenerateException;
import cn.idev.excel.test.util.TestFileUtil;
import cn.idev.excel.write.merge.LoopMergeStrategy;
import cn.idev.excel.write.metadata.WriteSheet;
import cn.idev.excel.write.metadata.fill.FillConfig;
import cn.idev.excel.write.metadata.fill.FillWrapper;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 *
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class FillDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;
    private static File simpleTemplate07;
    private static File simpleTemplate03;
    private static File simpleTemplateCsv;
    private static File fileComplex07;
    private static File complexFillTemplate07;
    private static File fileComplex03;
    private static File complexFillTemplate03;
    private static File fileHorizontal07;
    private static File horizontalFillTemplate07;
    private static File fileHorizontal03;
    private static File horizontalFillTemplate03;
    private static File byName07;
    private static File byName03;
    private static File byNameTemplate07;
    private static File byNameTemplate03;
    private static File fileComposite07;
    private static File compositeFillTemplate07;
    private static File fileComposite03;
    private static File compositeFillTemplate03;

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.createNewFile("fill07.xlsx");
        file03 = TestFileUtil.createNewFile("fill03.xls");
        fileCsv = TestFileUtil.createNewFile("fill.csv");
        simpleTemplate07 = TestFileUtil.readFile("fill" + File.separator + "simple.xlsx");
        simpleTemplate03 = TestFileUtil.readFile("fill" + File.separator + "simple.xls");
        simpleTemplateCsv = TestFileUtil.readFile("fill" + File.separator + "simple.csv");
        fileComplex07 = TestFileUtil.createNewFile("fillComplex07.xlsx");
        complexFillTemplate07 = TestFileUtil.readFile("fill" + File.separator + "complex.xlsx");
        fileComplex03 = TestFileUtil.createNewFile("fillComplex03.xls");
        complexFillTemplate03 = TestFileUtil.readFile("fill" + File.separator + "complex.xls");
        fileHorizontal07 = TestFileUtil.createNewFile("fillHorizontal07.xlsx");
        horizontalFillTemplate07 = TestFileUtil.readFile("fill" + File.separator + "horizontal.xlsx");
        fileHorizontal03 = TestFileUtil.createNewFile("fillHorizontal03.xls");
        horizontalFillTemplate03 = TestFileUtil.readFile("fill" + File.separator + "horizontal.xls");
        byName07 = TestFileUtil.createNewFile("byName07.xlsx");
        byNameTemplate07 = TestFileUtil.readFile("fill" + File.separator + "byName.xlsx");
        byName03 = TestFileUtil.createNewFile("byName03.xls");
        byNameTemplate03 = TestFileUtil.readFile("fill" + File.separator + "byName.xls");
        fileComposite07 = TestFileUtil.createNewFile("fileComposite07.xlsx");
        compositeFillTemplate07 = TestFileUtil.readFile("fill" + File.separator + "composite.xlsx");
        fileComposite03 = TestFileUtil.createNewFile("fileComposite03.xls");
        compositeFillTemplate03 = TestFileUtil.readFile("fill" + File.separator + "composite.xls");
    }

    @Test
    public void t01Fill07() {
        fill(file07, simpleTemplate07);
    }

    @Test
    public void t02Fill03() {
        fill(file03, simpleTemplate03);
    }

    @Test
    public void t03FillCsv() {
        ExcelGenerateException excelGenerateException =
                Assertions.assertThrows(ExcelGenerateException.class, () -> fill(fileCsv, simpleTemplateCsv));
        Assertions.assertEquals("csv cannot use template.", excelGenerateException.getMessage());
    }

    @Test
    public void t03ComplexFill07() {
        complexFill(fileComplex07, complexFillTemplate07);
    }

    @Test
    public void t04ComplexFill03() {
        complexFill(fileComplex03, complexFillTemplate03);
    }

    @Test
    public void t05HorizontalFill07() {
        horizontalFill(fileHorizontal07, horizontalFillTemplate07);
    }

    @Test
    public void t06HorizontalFill03() {
        horizontalFill(fileHorizontal03, horizontalFillTemplate03);
    }

    @Test
    public void t07ByNameFill07() {
        byNameFill(byName07, byNameTemplate07);
    }

    @Test
    public void t08ByNameFill03() {
        byNameFill(byName03, byNameTemplate03);
    }

    @Test
    public void t09CompositeFill07() {
        compositeFill(fileComposite07, compositeFillTemplate07);
    }

    @Test
    public void t10CompositeFill03() {
        compositeFill(fileComposite03, compositeFillTemplate03);
    }

    private void byNameFill(File file, File template) {
        FillData fillData = new FillData();
        fillData.setName("Zhang San");
        fillData.setNumber(5.2);
        EasyExcel.write(file, FillData.class)
                .withTemplate(template)
                .sheet("Sheet2")
                .doFill(fillData);
    }

    private void compositeFill(File file, File template) {
        try (ExcelWriter excelWriter =
                EasyExcel.write(file).withTemplate(template).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();

            FillConfig fillConfig = FillConfig.builder()
                    .direction(WriteDirectionEnum.HORIZONTAL)
                    .build();
            excelWriter.fill(new FillWrapper("data1", data()), fillConfig, writeSheet);
            excelWriter.fill(new FillWrapper("data1", data()), fillConfig, writeSheet);
            excelWriter.fill(new FillWrapper("data2", data()), writeSheet);
            excelWriter.fill(new FillWrapper("data2", data()), writeSheet);
            excelWriter.fill(new FillWrapper("data3", data()), writeSheet);
            excelWriter.fill(new FillWrapper("data3", data()), writeSheet);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", "2019-10-09 13:28:28");
            excelWriter.fill(map, writeSheet);
        }

        List<Object> list = EasyExcel.read(file)
                .ignoreEmptyRow(false)
                .sheet()
                .headRowNumber(0)
                .doReadSync();
        Map<String, String> map0 = (Map<String, String>) list.get(0);
        Assertions.assertEquals("Zhang San", map0.get(21));
        Map<String, String> map27 = (Map<String, String>) list.get(27);
        Assertions.assertEquals("Zhang San", map27.get(0));
        Map<String, String> map29 = (Map<String, String>) list.get(29);
        Assertions.assertEquals("Zhang San", map29.get(3));
    }

    private void horizontalFill(File file, File template) {
        try (ExcelWriter excelWriter =
                EasyExcel.write(file).withTemplate(template).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfig = FillConfig.builder()
                    .direction(WriteDirectionEnum.HORIZONTAL)
                    .build();
            excelWriter.fill(data(), fillConfig, writeSheet);
            excelWriter.fill(data(), fillConfig, writeSheet);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", "2019-10-09 13:28:28");
            excelWriter.fill(map, writeSheet);
            excelWriter.finish();
        }

        List<Object> list = EasyExcel.read(file).sheet().headRowNumber(0).doReadSync();
        Assertions.assertEquals(list.size(), 5L);
        Map<String, String> map0 = (Map<String, String>) list.get(0);
        Assertions.assertEquals("Zhang San", map0.get(2));
    }

    private void complexFill(File file, File template) {
        try (ExcelWriter excelWriter =
                EasyExcel.write(file).withTemplate(template).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet()
                    .registerWriteHandler(new LoopMergeStrategy(2, 0))
                    .build();
            FillConfig fillConfig =
                    FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(data(), fillConfig, writeSheet);
            excelWriter.fill(data(), fillConfig, writeSheet);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", "2019-10-09 13:28:28");
            map.put("total", 1000);
            excelWriter.fill(map, writeSheet);
        }
        List<Object> list = EasyExcel.read(file).sheet().headRowNumber(3).doReadSync();
        Assertions.assertEquals(list.size(), 21L);
        Map<String, String> map19 = (Map<String, String>) list.get(19);
        Assertions.assertEquals("Zhang San", map19.get(0));
    }

    private void fill(File file, File template) {
        FillData fillData = new FillData();
        fillData.setName("Zhang San");
        fillData.setNumber(5.2);
        EasyExcel.write(file, FillData.class).withTemplate(template).sheet().doFill(fillData);
    }

    private List<FillData> data() {
        List<FillData> list = new ArrayList<FillData>();
        for (int i = 0; i < 10; i++) {
            FillData fillData = new FillData();
            list.add(fillData);
            fillData.setName("Zhang San");
            fillData.setNumber(5.2);
            if (i == 5) {
                fillData.setName(null);
            }
        }
        return list;
    }
}
