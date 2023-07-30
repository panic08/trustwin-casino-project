package eu.panic.authservice.template.payload;

import eu.panic.authservice.template.enums.Gender;

public record ChangePersonalDataResponse(
        String birthday,
        Gender gender
) {
}
