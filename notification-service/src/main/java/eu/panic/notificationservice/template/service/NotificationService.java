package eu.panic.notificationservice.template.service;

import eu.panic.notificationservice.template.entity.Notification;
import eu.panic.notificationservice.template.payload.NotificationMessage;

import java.util.List;

public interface NotificationService {
    List<Notification> getLastNotifications(String jwtToken);
    List<Notification> watchAllNotifications(String jwtToken);
    void handleNotificationMessage(NotificationMessage notificationMessage);
}
