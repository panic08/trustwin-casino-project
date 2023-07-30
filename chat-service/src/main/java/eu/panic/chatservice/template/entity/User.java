package eu.panic.chatservice.template.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.panic.chatservice.template.enums.AuthorizeType;
import eu.panic.chatservice.template.enums.Gender;
import eu.panic.chatservice.template.enums.Rank;
import eu.panic.chatservice.template.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_table")
@Getter
@Setter
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Embedded
    private Data data;

    @Embedded
    private PersonalData personalData;

    @Embedded
    private RefData refData;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "is_multi_account", nullable = false)
    private Boolean isMultiAccount;

    @Column(name = "is_account_non_locked", nullable = false)
    private Boolean isAccountNonLocked;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "registeredAt", nullable = false)
    private Long registeredAt;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonalData{
        @Column(name = "nickname", nullable = true)
        private String nickname;

        @Column(name = "birthday", nullable = true)
        private String birthday;

        @Column(name = "gender", nullable = true)
        @Enumerated(EnumType.STRING)
        private Gender gender;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data{
        @Column(name = "authorize_type", nullable = false)
        @Enumerated(EnumType.STRING)
        private AuthorizeType authorizeType;
        @Column(name = "server_seed", nullable = false)
        private String serverSeed;
        @Column(name = "client_seed", nullable = false)
        private String clientSeed;
        @Column(name = "rank", nullable = false)
        @Enumerated(EnumType.STRING)
        private Rank rank;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefData{
        @Column(name = "invited", nullable = false)
        private Long invited;
        @Column(name = "earned", nullable = false)
        private Long earned;
        @Column(name = "level", nullable = false)
        private Integer level;
        @Column(name = "ref_link", nullable = false)
        private String refLink;
        @Column(name = "invited_by", nullable = true)
        private String invitedBy;
    }
}
