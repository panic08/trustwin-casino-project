package eu.panic.notificationservice.template.dto;

import eu.panic.notificationservice.template.enums.AuthorizeType;
import eu.panic.notificationservice.template.enums.Gender;
import eu.panic.notificationservice.template.enums.Rank;
import eu.panic.notificationservice.template.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Data data;
    private PersonalData personalData;
    private RefData refData;
    private Role role;
    private Long balance;
    private Boolean isMultiAccount;
    private Boolean isAccountNonLocked;
    private String ipAddress;
    private Long registeredAt;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonalData{
        private String nickname;
        private String birthday;
        private Gender gender;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data{
        private AuthorizeType authorizeType;
        private String serverSeed;
        private String clientSeed;
        private Rank rank;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefData{
        private Long invited;
        private Long earned;
        private Integer level;
        private String refLink;
        private String invitedBy;
    }
}
