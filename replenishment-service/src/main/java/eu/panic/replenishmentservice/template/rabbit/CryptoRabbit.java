package eu.panic.replenishmentservice.template.rabbit;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CryptoRabbit {
    private final String coinGeckoCoinProviderURL =
            "https://api.coingecko.com/api/v3/simple/price?ids=tron,bitcoin,ethereum,litecoin,matic-network,tether&vs_currencies=eur,usd";
    private final String btcWallet = "";
    private final String ethWallet = "";
    private final String ltcWallet = "";
    private final String trxWallet = "";
    private final String maticWallet = "";
    private final String tetherERC20Wallet = "";
    private final String etherScanApi = "";
}
