package lissa.trading.statisticsService.service.dataInitializer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("local")
public class MainDataInitializer {
    private final List<DataInitializerService> dataInitializerServices;

    @PostConstruct
    public void init() {
        dataInitializerServices.forEach(DataInitializerService::createData);
    }
}
