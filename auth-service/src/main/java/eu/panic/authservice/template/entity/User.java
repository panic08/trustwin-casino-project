package eu.panic.authservice.template.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.panic.authservice.template.enums.Gender;
import eu.panic.authservice.template.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "users_table")
@Getter
@Setter
public class User implements UserDetails {
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

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
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
        @Column(name = "firstname", nullable = true)
        private String firstname;

        @Column(name = "lastname", nullable = true)
        private String lastname;

        @Column(name = "birthday", nullable = true)
        private String birthday;

        @Column(name = "gender", nullable = true)
        @Enumerated(value = EnumType.STRING)
        private Gender gender;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data{
        @Column(name = "server_seed", nullable = false)
        private String serverSeed;
        @Column(name = "client_seed", nullable = false)
        private String clientSeed;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return  true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
