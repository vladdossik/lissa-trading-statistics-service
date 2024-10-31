package lissa.trading.statisticsService.service.consumer;

import java.util.List;

public interface StatsConsumer<T> {

    void receiveAllStats(List<T> stats);

    void receiveStatAfterUpdate(T stat);
}
