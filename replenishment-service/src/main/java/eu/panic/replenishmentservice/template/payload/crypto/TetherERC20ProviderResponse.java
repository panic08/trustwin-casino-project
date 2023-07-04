package eu.panic.replenishmentservice.template.payload.crypto;

import lombok.Getter;

import java.util.List;

@Getter
public class TetherERC20ProviderResponse {
    private String status;
    private String message;
    private List<ResultDTO> result;

    @Getter
    public static class ResultDTO {
        private String blockNumber;
        private String timeStamp;
        private String hash;
        private String nonce;
        private String blockHash;
        private String from;
        private String contractAddress;
        private String to;
        private String value;
        private String tokenName;
        private String tokenSymbol;
        private String tokenDecimal;
        private String transactionIndex;
        private String gas;
        private String gasPrice;
        private String gasUsed;
        private String cumulativeGasUsed;
        private String input;
        private String confirmations;
    }
}
