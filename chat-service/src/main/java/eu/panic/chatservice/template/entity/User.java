package eu.panic.chatservice.template.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.panic.chatservice.template.enums.AuthorizeType;
import eu.panic.chatservice.template.enums.Gender;
import eu.panic.chatservice.template.enums.Rank;
import eu.panic.chatservice.template.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
public class User {
    @Column("id")
    private Long id;

    @Column("username")
    private String username;

    @Column("password")
    @JsonIgnore
    private String password;

    @Column("email")
    private String email;

    private Data data;

    private PersonalData personalData;

    private RefData refData;

    @Column("role")
    private Role role;

    @Column("balance")
    private Long balance;

    @Column("is_multi_account")
    private Boolean isMultiAccount;

    @Column("is_account_non_locked")
    private Boolean isAccountNonLocked;

    @Column("ip_address")
    private String ipAddress;

    @Column("registeredAt")
    private Long registeredAt;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonalData{
        @Column("nickname")
        private String nickname;

        @Column("birthday")
        private String birthday;

        @Column("gender")
        private Gender gender;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data{
        @Column("authorize_type")
        private AuthorizeType authorizeType;
        @Column("server_seed")
        private String serverSeed;
        @Column("client_seed")
        private String clientSeed;
        @Column("rank")
        private Rank rank;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefData{
        @Column("invited")
        private Long invited;
        @Column("earned")
        private Long earned;
        @Column("level")
        private Integer level;
        @Column("ref_link")
        private String refLink;
        @Column("invited_by")
        private String invitedBy;
    }

}
