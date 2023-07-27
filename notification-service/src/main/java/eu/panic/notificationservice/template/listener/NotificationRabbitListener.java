package eu.panic.notificationservice.template.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.panic.notificationservice.template.payload.NotificationMessage;
import eu.panic.notificationservice.template.service.implement.NotificationServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "notification-queue")
public class NotificationRabbitListener {
    public NotificationRabbitListener(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    private final NotificationServiceImpl notificationService;
    @RabbitHandler
    private void handleNotificationMessage(String notificationMessage){
        ObjectMapper objectMapper = new ObjectMapper();

        NotificationMessage notificationMessage1 = objectMapper.convertValue(notificationMessage, NotificationMessage.class);

        notificationService.handleNotificationMessage(notificationMessage1);
    }
}
