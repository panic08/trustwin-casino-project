package eu.panic.chatservice.template.entity;

import eu.panic.chatservice.template.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "replenishments_table")
@Data
public class Replenishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "wallet_id", nullable = false)
    private String walletId;
    @Column(name = "win", nullable = false)
    private Double amount;
    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(name = "timestamp", nullable = false)
    private Long timestamp;
}
