package cn.idev.excel.test.core.style;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.HeadFontStyle;
import cn.idev.excel.annotation.write.style.HeadStyle;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@HeadStyle
@HeadFontStyle
public class StyleData {
    @ExcelProperty("字符串")
    private String string;

    @ExcelProperty("字符串1")
    private String string1;
}
