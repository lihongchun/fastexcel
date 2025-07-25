package cn.idev.excel.converters.localdatetime;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;
import cn.idev.excel.util.DateUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * LocalDateTime and number converter
 *
 *
 */
public class LocalDateTimeNumberConverter implements Converter<LocalDateTime> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public LocalDateTime convertToJavaData(
            ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return DateUtils.getLocalDateTime(
                    cellData.getNumberValue().doubleValue(), globalConfiguration.getUse1904windowing());
        } else {
            return DateUtils.getLocalDateTime(
                    cellData.getNumberValue().doubleValue(),
                    contentProperty.getDateTimeFormatProperty().getUse1904windowing());
        }
    }

    @Override
    public WriteCellData<?> convertToExcelData(
            LocalDateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new WriteCellData<>(
                    BigDecimal.valueOf(DateUtil.getExcelDate(value, globalConfiguration.getUse1904windowing())));
        } else {
            return new WriteCellData<>(BigDecimal.valueOf(DateUtil.getExcelDate(
                    value, contentProperty.getDateTimeFormatProperty().getUse1904windowing())));
        }
    }
}
