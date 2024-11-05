package lissa.trading.statisticsService.service.consumer;

import java.util.List;

public interface StatsConsumer<T> {

    void receiveUsersData(List<T> stats);
}
