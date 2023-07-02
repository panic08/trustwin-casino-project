package eu.panic.authservice.template.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sign_in_history_table")
@Getter
@Setter
public class SignInHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Embedded
    private Data data;

    @Column(name = "timestamp", nullable = false)
    private Long timestamp;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data{
        @Column(name = "ip_address")
        private String ipAddress;

        @Embedded
        private BrowserInfo browserInfo;

        @Embedded
        private DeviceInfo deviceInfo;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class DeviceInfo {
        @Column(name = "device_type", nullable = false)
        private String deviceType;

        @Column(name = "device_name", nullable = false)
        private String deviceName;

        @Column(name = "operating_system", nullable = false)
        private String operatingSystem;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class BrowserInfo {
        @Column(name = "browser_name", nullable = false)
        private String browserName;

        @Column(name = "browser_version", nullable = false)
        private String browserVersion;
    }
}
