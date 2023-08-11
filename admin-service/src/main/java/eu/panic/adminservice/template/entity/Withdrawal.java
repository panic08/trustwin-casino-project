package eu.panic.adminservice.template.entity;

import eu.panic.adminservice.template.enums.WithdrawalMethod;
import eu.panic.adminservice.template.enums.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "withdrawals_table")
@Getter
@Setter
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WithdrawalStatus status;
    @Column(name = "method", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WithdrawalMethod method;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "wallet_id", nullable = false)
    private String walletId;
    @Column(name = "win", nullable = false)
    private Long amount;
    @Column(name = "timestamp", nullable = false)
    private Long timestamp;
}
