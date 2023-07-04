package eu.panic.replenishmentservice.template.payload.crypto;

import lombok.Getter;

import java.util.List;

@Getter
public class PolygonProviderResponse {
    private List<ResponseDto> responseDtos;
    @Getter
    public static class ResponseDto {
        private String blockHash;
        private int blockNumber;
        private String from;
        private int gas;
        private long gasPrice;
        private String maxFeePerGas;
        private String maxPriorityFeePerGas;
        private String input;
        private int nonce;
        private String to;
        private int transactionIndex;
        private String value;
        private String type;
        private List<Object> accessList;
        private String chainId;
        private Object contractAddress;
        private String cumulativeGasUsed;
        private String effectiveGasPrice;
        private String gasUsed;
        private List<Log> logs;
        private String logsBloom;
        private boolean status;
        private String transactionHash;
        private String hash;
        private long timestamp;
        @Getter
        public static class Log {
            private String address;
            private List<String> topics;
            private String data;
            private int blockNumber;
            private String transactionHash;
            private int transactionIndex;
            private String blockHash;
            private int logIndex;
            private boolean removed;
        }
    }
}