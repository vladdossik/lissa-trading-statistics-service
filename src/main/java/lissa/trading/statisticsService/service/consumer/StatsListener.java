package lissa.trading.statisticsService.service.consumer;

import java.util.List;

public interface StatsListener<T> {

    void receiveUsersData(List<T> stats);
}
