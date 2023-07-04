package eu.panic.replenishmentservice.template.payload.crypto;

import lombok.Getter;

import java.util.List;
@Getter
public class EthereumProviderResponse {
    private String status;
    private String message;
    private List<TransactionDto> result;
    @Getter
    public static class TransactionDto {
        private String blockNumber;
        private String timeStamp;
        private String hash;
        private String nonce;
        private String blockHash;
        private String transactionIndex;
        private String from;
        private String to;
        private String value;
        private String gas;
        private String gasPrice;
        private String isError;
        private String txreceipt_status;
        private String input;
        private String contractAddress;
        private String cumulativeGasUsed;
        private String gasUsed;
        private String confirmations;
        private String methodId;
        private String functionName;
    }
}
