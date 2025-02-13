package lissa.trading.statisticsService.repository;

import lissa.trading.statisticsService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByExternalIdIn(List<UUID> externalIds);
}
