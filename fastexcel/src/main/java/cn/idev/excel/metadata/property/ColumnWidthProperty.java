package cn.idev.excel.metadata.property;

import cn.idev.excel.annotation.write.style.ColumnWidth;

/**
 * Configuration from annotations
 *
 *
 */
public class ColumnWidthProperty {
    private Integer width;

    public ColumnWidthProperty(Integer width) {
        this.width = width;
    }

    public static ColumnWidthProperty build(ColumnWidth columnWidth) {
        if (columnWidth == null || columnWidth.value() < 0) {
            return null;
        }
        return new ColumnWidthProperty(columnWidth.value());
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
