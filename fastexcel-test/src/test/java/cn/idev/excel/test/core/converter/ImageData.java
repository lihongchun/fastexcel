package cn.idev.excel.test.core.converter;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.converters.string.StringImageConverter;
import java.io.File;
import java.io.InputStream;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(500)
@ColumnWidth(500 / 8)
public class ImageData {
    private File file;
    private InputStream inputStream;

    @ExcelProperty(converter = StringImageConverter.class)
    private String string;

    private byte[] byteArray;
}
