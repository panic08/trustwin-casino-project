package eu.panic.replenishmentservice.template.hash;

import eu.panic.replenishmentservice.template.enums.CryptoCurrency;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("replenishments_hash")
@Getter
@Setter
public class CryptoReplenishmentHash {
    @Id
    private String id;
    @Indexed
    private String username;
    private String walletId;
    private double amount;
    private CryptoCurrency currency;
    private long timestamp;
}
