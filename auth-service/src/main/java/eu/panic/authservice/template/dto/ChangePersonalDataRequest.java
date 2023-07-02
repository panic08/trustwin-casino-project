package eu.panic.authservice.template.dto;

import eu.panic.authservice.template.enums.Gender;
import lombok.Getter;
public record ChangePersonalDataRequest(
        String firstname,
        String lastname,
        String birthday,
        Gender gender
) {
}
