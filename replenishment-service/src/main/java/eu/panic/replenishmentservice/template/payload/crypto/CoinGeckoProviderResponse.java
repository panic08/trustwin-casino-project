package eu.panic.replenishmentservice.template.payload.crypto;

import lombok.Getter;

@Getter
public class CoinGeckoProviderResponse {
    private CoinDto bitcoin;
    private CoinDto ethereum;
    private CoinDto litecoin;
    private CoinDto tron;
    private CoinDto ripple;
    private CoinDto tether;
    private CoinDto maticNetwork;

    @Getter
    public static class CoinDto {
        private double usd;
        private double eur;
    }
}
