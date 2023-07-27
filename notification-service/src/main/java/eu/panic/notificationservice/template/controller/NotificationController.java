package eu.panic.notificationservice.template.controller;

import eu.panic.notificationservice.template.entity.Notification;
import eu.panic.notificationservice.template.service.implement.NotificationServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    private final NotificationServiceImpl notificationService;
    @GetMapping("/getLastFive")
    private List<Notification> getLastNotifications(@RequestHeader String jwtToken){
        return notificationService.getLastNotifications(jwtToken);
    }

    @PostMapping("/watch")
    private List<Notification> watchAllNotifications(@RequestHeader String jwtToken){
        return notificationService.watchAllNotifications(jwtToken);
    }

}
