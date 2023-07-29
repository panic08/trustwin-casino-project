package eu.panic.managementgameservice.template.entity;

import eu.panic.managementgameservice.template.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
public class Replenishment {
    @Column("id")
    private Long id;
    @Column("username")
    private String username;
    @Column("wallet_id")
    private String walletId;
    @Column("amount")
    private Double amount;
    @Column("payment_method")
    private PaymentMethod paymentMethod;
    @Column("timestamp")
    private Long timestamp;
}
