package eu.panic.authservice.template.payload;

import eu.panic.authservice.template.enums.Gender;

public record ChangePersonalDataRequest(
        String nickname,
        String birthday,
        Gender gender
) {
}
