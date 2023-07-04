package eu.panic.replenishmentservice.template.payload.crypto;

import lombok.Getter;

import java.util.List;

@Getter
public class LitecoinProviderResponse {
    private List<ReplenishmentDto> replenishments;

    @Getter
    public static class ReplenishmentDto {
        private int blockNumber;
        private String fee;
        private String hash;
        private String hex;
        private int index;
        private List<InputDto> inputs;
        private int locktime;
        private List<OutputDto> outputs;
        private int size;
        private int time;
        private int version;
        private int vsize;
        private int weight;
        private String witnessHash;

        @Getter
        public static class InputDto {
            private PrevoutDto prevout;
            private int sequence;
            private String script;
            private CoinDto coin;

            @Getter
            public static class PrevoutDto {
                private String hash;
                private int index;
            }

            @Getter
            public static class CoinDto {
                private int version;
                private int height;
                private String value;
                private String script;
                private String address;
                private boolean coinbase;
            }
        }

        @Getter
        public static class OutputDto {
            private String value;
            private String script;
            private String address;
        }
    }
}
