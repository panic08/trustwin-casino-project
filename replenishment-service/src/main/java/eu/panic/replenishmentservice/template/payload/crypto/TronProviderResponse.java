package eu.panic.replenishmentservice.template.payload.crypto;

import lombok.Getter;

import java.util.List;

@Getter
public class TronProviderResponse {
    private List<Data> data;
    @Getter
    public static class Data {
        private Ret[] ret;
        private String[] signature;
        private String txID;
        private int net_usage;
        private String raw_data_hex;
        private int net_fee;
        private int energy_usage;
        private int blockNumber;
        private long block_timestamp;
        private int energy_fee;
        private int energy_usage_total;
        private RawData raw_data;
        private Object[] internal_transactions;
    }
    @Getter
    public static class Ret {
        private String contractRet;
        private int fee;
    }
    @Getter
    public static class RawData {
        private List<Contract> contract;
        private String ref_block_bytes;
        private String ref_block_hash;
        private long expiration;
        private long timestamp;
    }
    @Getter
    public static class Contract {
        private Parameter parameter;
        private String type;
    }
    @Getter
    public static class Parameter {
        private Value value;
        private String type_url;
    }
    @Getter
    public static class Value {
        private long amount;
        private String owner_address;
        private String to_address;
        private String asset_name;
    }
}
