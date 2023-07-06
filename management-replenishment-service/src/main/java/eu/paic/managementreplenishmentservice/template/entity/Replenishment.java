package eu.paic.managementreplenishmentservice.template.entity;

import eu.paic.managementreplenishmentservice.template.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "replenishments_table")
@Getter
@Setter
public class Replenishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "wallet_id", nullable = false)
    private String walletId;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Column(name = "payment_method", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;
    private Long timestamp;
}