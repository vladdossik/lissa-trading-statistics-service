package lissa.trading.statisticsService.service;

import lissa.trading.statisticsService.repository.UserRepository;
import lissa.trading.statisticsService.client.user.feign.UserServiceClient;
import lissa.trading.statisticsService.dto.UserReportDto;
import lissa.trading.statisticsService.mapper.UserMapper;
import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.UserJson;
import lissa.trading.statisticsService.service.userReport.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest extends AbstractServiceInitialization {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<List<User>> captor;

    @Test
    public void getUsersForReport_Returns4Users() {
        when(userServiceClient.getUserIdsWithPaginationAndFilters(PAGEABLE, "firstName", "lastName"))
                .thenReturn(uuids);

        when(userRepository.findAllByExternalIdIn(uuids)).thenReturn(users);

        when(userMapper.toUserReportDto(any())).thenAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgument(0);
            return UserReportDto.builder()
                    .externalId(user.getExternalId())
                    .firstName(user.getUserJson().getFirstName())
                    .lastName(user.getUserJson().getLastName())
                    .build();
        });

        List<UserReportDto> response = userService.getUsersForReport(PAGEABLE, "firstName", "lastName");
        assertNotNull(response);
        assertEquals(4, response.size());
        assertEquals(users.get(0).getExternalId(), response.get(0).getExternalId());
        assertEquals(users.get(2).getExternalId(), response.get(2).getExternalId());
    }

    @Test
    public void getUsersForReport_Returns0Users() {
        when(userServiceClient.getUserIdsWithPaginationAndFilters(PAGEABLE, "firstName", "lastName"))
                .thenReturn(Collections.emptyList());

        List<UserReportDto> response = userService.getUsersForReport(PAGEABLE, "firstName", "lastName");

        verify(userRepository, never()).findAllByExternalIdIn(any());
        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    public void processUsers_ShouldUpdateAndCreateUsers() {
        processMocksForUpdateAndCreateUsers();

        userService.processUsers(userReportDtos);

        verify(userRepository).findAllByExternalIdIn(uuids);
        verify(userRepository).saveAll(captor.capture());

        List<User> savedUsers = captor.getValue();
        assertNotNull(savedUsers);
        assertEquals(4, savedUsers.size());

        User updatedUser = savedUsers.stream()
                .filter(user -> user.getExternalId().equals(userReportDtos.get(0).getExternalId()))
                .findFirst().orElseThrow();
        assertEquals("newFirstName", updatedUser.getUserJson().getFirstName());

        User newSavedUser = savedUsers.stream()
                .filter(user -> user.getExternalId().equals(userReportDtos.get(3).getExternalId()))
                .findFirst().orElseThrow();
        assertEquals(userReportDtos.get(3).getFirstName(), newSavedUser.getUserJson().getFirstName());
    }

    @Test
    public void processUsers_WhenEmptyList() {
        userService.processUsers(Collections.emptyList());

        verify(userRepository).findAllByExternalIdIn(Collections.emptyList());
        verify(userRepository).saveAll(Collections.emptyList());
        verifyNoInteractions(userMapper);
    }

    @Test
    public void processUsers_ShouldCreateAllUsers() {
        processMocksForCreateAllUsers();

        userService.processUsers(userReportDtos);

        verify(userMapper, never()).updateUser(any(), any());
        verify(userRepository).saveAll(captor.capture());

        List<User> savedUsers = captor.getValue();
        assertNotNull(savedUsers);
        assertEquals(4, savedUsers.size());

        for (int i = 0; i < 4; i++) {
            assertEquals(savedUsers.get(i).getExternalId(), userReportDtos.get(i).getExternalId());
        }
    }

    @Test
    public void processUsers_ShouldUpdateAllUsers() {
        processMockForUpdateAllUsers();

        userService.processUsers(userReportDtos);

        verify(userMapper, never()).createUser(any(), any());
        verify(userRepository).saveAll(captor.capture());

        List<User> savedUsers = captor.getValue();
        assertNotNull(savedUsers);
        assertEquals(4, savedUsers.size());
        assertEquals("newFirstName1", savedUsers.get(0).getUserJson().getFirstName());
        assertEquals("newFirstName4", savedUsers.get(3).getUserJson().getFirstName());
    }

    private void processMocksForUpdateAndCreateUsers() {
        fillExistingUsersList();

        when(userRepository.findAllByExternalIdIn(uuids)).thenReturn(existingUsers);

        when(userMapper.toUserJson(any())).thenReturn(existingUsers.get(0).getUserJson());

        when(userMapper.updateUser(any(), any())).thenAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgument(1);
            user.getUserJson().setFirstName("newFirstName");
            return user;
        });

        when(userMapper.createUser(any(), any())).thenAnswer(invocationOnMock -> {
            UserReportDto userReportDto = invocationOnMock.getArgument(0);
            return User.builder()
                    .externalId(userReportDto.getExternalId())
                    .userJson(UserJson.builder()
                            .firstName(userReportDto.getFirstName())
                            .lastName(userReportDto.getLastName())
                            .build())
                    .build();
        });
    }

    private void processMocksForCreateAllUsers() {
        when(userRepository.findAllByExternalIdIn(uuids)).thenReturn(Collections.emptyList());

        when(userMapper.toUserJson(any())).thenReturn(users.get(0).getUserJson());

        when(userMapper.createUser(any(), any())).thenAnswer(invocationOnMock -> {
            UserReportDto userReportDto = invocationOnMock.getArgument(0);
            return User.builder()
                    .externalId(userReportDto.getExternalId())
                    .userJson(UserJson.builder()
                            .firstName(userReportDto.getFirstName())
                            .lastName(userReportDto.getLastName())
                            .build())
                    .build();
        });
    }

    private void processMockForUpdateAllUsers() {
        existingUsers = userReportDtos.stream()
                .map(userReportDto -> new User(userReportDto.getExternalId(),
                        UserJson.builder()
                                .firstName(userReportDto.getFirstName())
                                .lastName(userReportDto.getLastName())
                                .build()))
                .toList();

        when(userRepository.findAllByExternalIdIn(uuids)).thenReturn(existingUsers);

        when(userMapper.toUserJson(any())).thenReturn(existingUsers.get(0).getUserJson());

        Map<UUID, String> newNames = new HashMap<>();

        for (int i = 0; i < userReportDtos.size(); i++) {
            newNames.put(userReportDtos.get(i).getExternalId(), "newFirstName" + (i + 1));
        }

        when(userMapper.updateUser(any(), any())).thenAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgument(1);
            user.getUserJson().setFirstName(newNames.get(user.getExternalId()));
            return user;
        });
    }

    private void fillExistingUsersList() {
        for (int i = 0; i < 2; i++) {
            UserReportDto userReportDto = userReportDtos.get(i);
            existingUsers.add(User.builder()
                    .externalId(userReportDto.getExternalId())
                    .userJson(UserJson.builder()
                            .firstName(userReportDto.getFirstName())
                            .lastName(userReportDto.getLastName())
                            .build())
                    .build());
        }
    }
}