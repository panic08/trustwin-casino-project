package eu.panic.replenishmentservice.template.payload.crypto;

import lombok.Getter;

import java.util.List;

@Getter
public class BitcoinProviderResponse {
    private String hash160;
    private String address;
    private int n_tx;
    private int n_unredeemed;
    private int total_received;
    private int total_sent;
    private int final_balance;
    private List<TransactionDTO> txs;
    @Getter
    public static class TransactionDTO {
        private String hash;
        private int ver;
        private int vin_sz;
        private int vout_sz;
        private int size;
        private int weight;
        private int fee;
        private String relayed_by;
        private int lock_time;
        private long tx_index;
        private boolean double_spend;
        private long time;
        private int block_index;
        private int block_height;
        private List<InputDTO> inputs;
        private List<OutputDTO> out;
        private int result;
        private int balance;
    }
    @Getter
    public static class InputDTO {
        private long sequence;
        private String witness;
        private String script;
        private int index;
        private PrevOutDTO prev_out;
    }
    @Getter
    public static class PrevOutDTO {
        private String addr;
        private int n;
        private String script;
        private List<SpendingOutpointDTO> spending_outpoints;
        private boolean spent;
        private long tx_index;
        private int type;
        private int value;
    }
    @Getter
    public static class SpendingOutpointDTO {
        private int n;
        private long tx_index;
    }
    @Getter
    public static class OutputDTO {
        private int type;
        private boolean spent;
        private int value;
        private List<SpendingOutpointDTO> spending_outpoints;
        private int n;
        private long tx_index;
        private String script;
        private String addr;
    }
}
