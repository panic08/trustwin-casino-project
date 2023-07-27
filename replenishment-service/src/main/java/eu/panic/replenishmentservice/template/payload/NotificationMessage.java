package eu.panic.replenishmentservice.template.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationMessage {
    private String username;
    private String message;
}
