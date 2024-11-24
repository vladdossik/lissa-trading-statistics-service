package lissa.trading.statisticsService.client.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "UserServiceClient", url = "${integration.rest.user-service-url}/v1/internal")
public interface UserServiceClient {
    @GetMapping("/get-user-ids")
    List<UUID> getUserIdsWithPaginationAndFilters(Pageable pageable,
                                                  @RequestParam(required = false)
                                                  String firstName,
                                                  @RequestParam(required = false)
                                                  String lastName);
}
