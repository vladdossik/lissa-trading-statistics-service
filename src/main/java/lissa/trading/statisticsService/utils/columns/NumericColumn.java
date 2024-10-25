package lissa.trading.statisticsService.utils.columns;

import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
public class NumericColumn<T> implements ExportColumn<T, Number> {

    private String columnName;
    private Function<T, Number> columnValue;

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public Number getColumnValue(T t) {
        return columnValue.apply(t);
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.NUMERIC;
    }
}
