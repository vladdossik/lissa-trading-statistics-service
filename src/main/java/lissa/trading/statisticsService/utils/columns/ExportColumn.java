package lissa.trading.statisticsService.utils.columns;

public interface ExportColumn<T, R> {
    String getColumnName();

    R getColumnValue(T t);

    ColumnType getColumnType();
}
