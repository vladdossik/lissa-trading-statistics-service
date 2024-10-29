package lissa.trading.statisticsService.utils.columns;

import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
public class TextColumn<T> implements ExportColumn<T, Object> {

    private final String columnName;
    private final Function<T, Object> columnValue;

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public Object getColumnValue(T t) {
        return columnValue.apply(t);
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.TEXT;
    }
}
