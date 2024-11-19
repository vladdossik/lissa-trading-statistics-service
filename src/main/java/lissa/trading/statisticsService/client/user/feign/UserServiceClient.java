package lissa.trading.statisticsService.client.user.feign;

import lissa.trading.statisticsService.dto.UsersIdResponseDto;
import lissa.trading.statisticsService.page.CustomPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "UserServiceClient", url = "${integration.rest.user-service-url}/v1/internal")
public interface UserServiceClient {
    @GetMapping("/get-users-id")
    CustomPage<UsersIdResponseDto> getUsersIdWithPaginationAndFilters(Pageable pageable,
                                                                      @RequestParam(required = false)
                                                                      String firstName,
                                                                      @RequestParam(required = false)
                                                                      String lastName);
}
