package eu.panic.notificationservice.template.service.implement;

import eu.panic.notificationservice.template.dto.UserDto;
import eu.panic.notificationservice.template.entity.Notification;
import eu.panic.notificationservice.template.exception.InvalidCredentialsException;
import eu.panic.notificationservice.template.payload.NotificationMessage;
import eu.panic.notificationservice.template.repository.NotificationRepository;
import eu.panic.notificationservice.template.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    public NotificationServiceImpl(RestTemplate restTemplate, NotificationRepository notificationRepository) {
        this.restTemplate = restTemplate;
        this.notificationRepository = notificationRepository;
    }

    private final RestTemplate restTemplate;
    private final NotificationRepository notificationRepository;
    private static final String JWT_URL = "http://localhost:8080/api/auth/getInfoByJwt";
    @Override
    public List<Notification> getLastNotifications(String jwtToken) {
        log.info("Starting method getLastNotifications on service {} method: getLastNotifications", NotificationServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: getLastNotifications", NotificationServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: getLastNotifications", NotificationServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        return notificationRepository.findTop5ByUsernameOrderByTimestampDesc(userDto.getUsername());
    }

    @Override
    public List<Notification> watchAllNotifications(String jwtToken) {
        log.info("Starting method watchAllNotifications on service {} method: watchAllNotifications", NotificationServiceImpl.class);

        log.info("Receiving entity user by JWT token on service {} method: watchAllNotifications", NotificationServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()){
            log.warn("Incorrect JWT token on service {} method: watchAllNotifications", NotificationServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        notificationRepository.updateLast5NotificationsToWatched(userDto.getUsername());

        return notificationRepository.findTop5ByUsernameOrderByTimestampDesc(userDto.getUsername());
    }

    @Override
    public void handleNotificationMessage(NotificationMessage notificationMessage) {
        log.info("Starting method handleNotificationMessage on service {} method: handleNotificationMessage", NotificationServiceImpl.class);

        log.info("Creating new entity notification on service {} method: handleNotificationMessage", NotificationServiceImpl.class);

        Notification notification = new Notification();

        notification.setUsername(notificationMessage.getUsername());
        notification.setIsWatched(false);
        notification.setMessage(notificationMessage.getMessage());
        notification.setTimestamp(System.currentTimeMillis());

        log.info("Saving an entity notification on service {} method: handleNotificationMessage", NotificationServiceImpl.class);

        notificationRepository.save(notification);

    }
}
