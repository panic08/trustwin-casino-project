package eu.panic.replenishmentservice.template.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.panic.replenishmentservice.template.dto.UserDto;
import eu.panic.replenishmentservice.template.enums.CryptoCurrency;
import eu.panic.replenishmentservice.template.exception.InsufficientTopUpAmountException;
import eu.panic.replenishmentservice.template.exception.InvalidCredentialsException;
import eu.panic.replenishmentservice.template.exception.PaymentsExistsException;
import eu.panic.replenishmentservice.template.hash.CryptoReplenishmentHash;
import eu.panic.replenishmentservice.template.payload.CryptoReplenishmentMessage;
import eu.panic.replenishmentservice.template.payload.CryptoReplenishmentRequest;
import eu.panic.replenishmentservice.template.payload.CryptoReplenishmentResponse;
import eu.panic.replenishmentservice.template.payload.crypto.*;
import eu.panic.replenishmentservice.template.rabbit.CryptoRabbit;
import eu.panic.replenishmentservice.template.repository.ReplenishmentHashRepository;
import eu.panic.replenishmentservice.template.service.CryptoReplenishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


@Service
@Slf4j
public class CryptoReplenishmentServiceImpl implements CryptoReplenishmentService {
    public CryptoReplenishmentServiceImpl(RabbitTemplate rabbitTemplate, CryptoRabbit cryptoRabbit, RestTemplate restTemplate, ReplenishmentHashRepository replenishmentHashRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.cryptoRabbit = cryptoRabbit;
        this.restTemplate = restTemplate;
        this.replenishmentHashRepository = replenishmentHashRepository;
    }

    private final RabbitTemplate rabbitTemplate;
    private final CryptoRabbit cryptoRabbit;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String JWT_URL = "http://localhost:8080/api/auth/getInfoByJwt";
    private final ReplenishmentHashRepository replenishmentHashRepository;
    @Override
    public CryptoReplenishmentResponse handlePayByBitcoin(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest) {
        log.info("Starting method handlePayByBitcoin on service {} method: handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                if (cryptoReplenishmentRequest.amount() < 1.2){
                    log.warn("Minimal amount for BTC payment is 1.2 USD on service {} method handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for BTC payment is 1.2 USD");
                }
            }
            case EUR -> {
                if (cryptoReplenishmentRequest.amount() < 1.1){
                    log.warn("Minimal amount for BTC payment is 1.1 EUR on service {} method handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for BTC payment is 1.1 EUR");
                }
            }
        }

        log.info("Receiving entity User by JWT on service {} method: handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()) {
            log.warn("Incorrect JWT token on service {} method handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Checking on payments exists on service {} method: handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);

        List<CryptoReplenishmentHash> cryptoReplenishmentHashList =
                replenishmentHashRepository.findCryptoReplenishmentHashesByUsername(userDto.getUsername());

        for (CryptoReplenishmentHash key : cryptoReplenishmentHashList){
            if (key.getCurrency().equals(CryptoCurrency.BTC)){
                log.warn("Complete the previous payment before starting a new one on service {} method: handlePayByBitcoin",
                        CryptoReplenishmentServiceImpl.class);
                throw new PaymentsExistsException("Complete the previous payment before starting a new one");
            }
        }

        log.info("Receiving the cryptocurrency rate on service {} method: handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<CoinGeckoProviderResponse> coinGeckoProviderResponseResponseEntity =
                restTemplate.getForEntity(cryptoRabbit.getCoinGeckoCoinProviderURL(), CoinGeckoProviderResponse.class);

        CoinGeckoProviderResponse coinGeckoProviderResponse = coinGeckoProviderResponseResponseEntity.getBody();

        log.info("Creating a response for this method on service {} method: handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentResponse cryptoReplenishmentResponse = new CryptoReplenishmentResponse();

        cryptoReplenishmentResponse.setWalletId(cryptoRabbit.getBtcWallet());
        cryptoReplenishmentResponse.setCurrency(CryptoCurrency.BTC);
        cryptoReplenishmentResponse.setTimestamp(System.currentTimeMillis());

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getBitcoin().getUsd() ) * 1e7
                ) / 1e7);
            }
            case EUR -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getBitcoin().getEur() ) * 1e7
                ) / 1e7);
            }
        }

        log.info("Creating a new hash replenishmentHash on service {} method: handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentHash cryptoReplenishmentHash = new CryptoReplenishmentHash();

        cryptoReplenishmentHash.setId(UUID.randomUUID().toString());
        cryptoReplenishmentHash.setUsername(userDto.getUsername());
        cryptoReplenishmentHash.setWalletId(cryptoReplenishmentResponse.getWalletId());
        cryptoReplenishmentHash.setAmount(cryptoReplenishmentResponse.getAmount());
        cryptoReplenishmentHash.setCurrency(cryptoReplenishmentResponse.getCurrency());
        cryptoReplenishmentHash.setTimestamp(cryptoReplenishmentResponse.getTimestamp());

        replenishmentHashRepository.save(cryptoReplenishmentHash);

        log.info("Create a new thread to check payment status on service {} method: handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);

        new Thread(() -> {
            log.info("Start a timer to check the payment status on service {} method: handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);

            long currentTime = System.currentTimeMillis();

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int counter = 0;
                public void run() {
                    log.info("Receiving our bitcoin transactions history by address on service {} method: handlePayByBitcoin",
                            CryptoReplenishmentServiceImpl.class);

                    ResponseEntity<BitcoinProviderResponse> bitcoinResponseDto = restTemplate
                            .getForEntity("https://blockchain.info/rawaddr/" + cryptoRabbit.getBtcWallet() + "?limit=7", BitcoinProviderResponse.class);

                    for (BitcoinProviderResponse.TransactionDTO tx : bitcoinResponseDto.getBody().getTxs()) {
                        for (BitcoinProviderResponse.InputDTO input : tx.getInputs()) {
                            if(input.getPrev_out().getValue() / 1e8 == cryptoReplenishmentResponse.getAmount()
                                    && tx.getTime() > currentTime && !(input.getPrev_out().getAddr().equals(cryptoRabbit.getBtcWallet()))
                            ){
                                log.info("Payment was founded on service {} method: handlePayByBitcoin", CryptoReplenishmentServiceImpl.class);

                                CryptoReplenishmentMessage cryptoReplenishmentMessage = new CryptoReplenishmentMessage(
                                        cryptoReplenishmentHash.getUsername(),
                                        input.getPrev_out().getAddr(),
                                        cryptoReplenishmentHash.getAmount(),
                                        new CryptoReplenishmentMessage.Exchange(
                                                coinGeckoProviderResponse.getBitcoin().getUsd(),
                                                coinGeckoProviderResponse.getEthereum().getUsd(),
                                                coinGeckoProviderResponse.getLitecoin().getUsd(),
                                                coinGeckoProviderResponse.getTron().getUsd(),
                                                coinGeckoProviderResponse.getTether().getUsd(),
                                                coinGeckoProviderResponse.getMaticNetwork().getUsd()

                                        ),
                                        cryptoReplenishmentHash.getCurrency(),
                                        cryptoReplenishmentHash.getTimestamp()
                                );

                                log.info("Deleting old payment-hash on service {} method: handlePayByBitcoin",
                                        CryptoReplenishmentServiceImpl.class);

                                replenishmentHashRepository.delete(cryptoReplenishmentHash);

                                String jsonMessage = null;

                                try {
                                    jsonMessage = objectMapper.writeValueAsString(cryptoReplenishmentMessage);
                                }catch (JsonProcessingException jsonProcessingException){
                                    jsonProcessingException.printStackTrace();
                                }

                                log.info("Sending a replenishment to replenishment-queue on service {} method: handlePayByBitcoin",
                                        CryptoReplenishmentServiceImpl.class);

                                rabbitTemplate.convertAndSend("replenishment-queue", jsonMessage);
                                return;
                            }
                        }
                    }

                    counter++;

                    if (counter == 12 * 4) {
                        log.warn("Payment is not found, timer is rollback on service {} method: handlePayByBitcoin",
                                CryptoReplenishmentServiceImpl.class);

                        log.info("Deleting old payment-hash on service {} method: handlePayByBitcoin",
                                CryptoReplenishmentServiceImpl.class);

                        replenishmentHashRepository.delete(cryptoReplenishmentHash);
                        timer.cancel();
                    }
                }
            };

            long delay = 0;
            long period = 4000;

            timer.scheduleAtFixedRate(task, delay, period);
        }).start();

        return cryptoReplenishmentResponse;
    }

    @Override
    public CryptoReplenishmentResponse handlePayByEthereum(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest) {
        log.info("Starting method handlePayByEthereum on service {} method: handlePayByEthereum", CryptoReplenishmentServiceImpl.class);

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                if (cryptoReplenishmentRequest.amount() < 2.8){
                    log.warn("Minimal amount for BTC payment is 2.8 USD on service {} method handlePayByEthereum", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for ETH payment is 2.8 USD");
                }
            }
            case EUR -> {
                if (cryptoReplenishmentRequest.amount() < 2.6){
                    log.warn("Minimal amount for BTC payment is 2.6 EUR on service {} method handlePayByEthereum", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for ETH payment is 2.6 EUR");
                }
            }
        }

        log.info("Receiving entity User by JWT on service {} method: handlePayByEthereum", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()) {
            log.warn("Incorrect JWT token on service {} method handlePayByEthereum", CryptoReplenishmentServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Checking on payments exists on service {} method: handlePayByEthereum", CryptoReplenishmentServiceImpl.class);

        List<CryptoReplenishmentHash> cryptoReplenishmentHashList =
                replenishmentHashRepository.findCryptoReplenishmentHashesByUsername(userDto.getUsername());

        for (CryptoReplenishmentHash key : cryptoReplenishmentHashList){
            if (key.getCurrency().equals(CryptoCurrency.ETH)){
                log.warn("Complete the previous payment before starting a new one on service {} method: handlePayByEthereum",
                        CryptoReplenishmentServiceImpl.class);
                throw new PaymentsExistsException("Complete the previous payment before starting a new one");
            }
        }

        log.info("Receiving the cryptocurrency rate on service {} method: handlePayByEthereum", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<CoinGeckoProviderResponse> coinGeckoProviderResponseResponseEntity =
                restTemplate.getForEntity(cryptoRabbit.getCoinGeckoCoinProviderURL(), CoinGeckoProviderResponse.class);

        CoinGeckoProviderResponse coinGeckoProviderResponse = coinGeckoProviderResponseResponseEntity.getBody();

        log.info("Creating a response for this method on service {} method: handlePayByEthereum", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentResponse cryptoReplenishmentResponse = new CryptoReplenishmentResponse();

        cryptoReplenishmentResponse.setWalletId(cryptoRabbit.getEthWallet());
        cryptoReplenishmentResponse.setCurrency(CryptoCurrency.ETH);
        cryptoReplenishmentResponse.setTimestamp(System.currentTimeMillis());

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getEthereum().getUsd() ) * 1e5
                ) / 1e5);
            }
            case EUR -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getEthereum().getEur() ) * 1e5
                ) / 1e5);
            }
        }

        log.info("Creating a new hash replenishmentHash on service {} method: handlePayByEthereum", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentHash cryptoReplenishmentHash = new CryptoReplenishmentHash();

        cryptoReplenishmentHash.setId(UUID.randomUUID().toString());
        cryptoReplenishmentHash.setUsername(userDto.getUsername());
        cryptoReplenishmentHash.setWalletId(cryptoReplenishmentResponse.getWalletId());
        cryptoReplenishmentHash.setAmount(cryptoReplenishmentResponse.getAmount());
        cryptoReplenishmentHash.setCurrency(cryptoReplenishmentResponse.getCurrency());
        cryptoReplenishmentHash.setTimestamp(cryptoReplenishmentResponse.getTimestamp());

        replenishmentHashRepository.save(cryptoReplenishmentHash);

        log.info("Create a new thread to check payment status on service {} method: handlePayByEthereum", CryptoReplenishmentServiceImpl.class);

        new Thread(() -> {
            log.info("Start a timer to check the payment status on service {} method: handlePayByEthereum", CryptoReplenishmentServiceImpl.class);

            long currentTime = System.currentTimeMillis();

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int counter = 0;
                public void run() {
                    log.info("Receiving our ethereum transactions history by address on service {} method: handlePayByEthereum",
                            CryptoReplenishmentServiceImpl.class);

                    ResponseEntity<EthereumProviderResponse> ethereumResponseDto = restTemplate
                            .getForEntity("https://api.etherscan.io/api?module=account&action=txlist&address="
                                    + cryptoRabbit.getEthWallet() + "&startblock=0&endblock=99999999&sort=desc&apikey="
                                    + cryptoRabbit.getEtherScanApi() + "&offset=0&limit=7", EthereumProviderResponse.class);

                    for (EthereumProviderResponse.TransactionDto transactionDto : ethereumResponseDto.getBody().getResult()) {
                        if ((double) Long.parseLong(transactionDto.getValue())/1e18 == cryptoReplenishmentResponse.getAmount()
                                &&
                                Long.parseLong(transactionDto.getTimeStamp()) > currentTime
                                &&
                                !(transactionDto.getFrom().equals(cryptoRabbit.getEthWallet()))
                        ){
                                log.info("Payment was founded on service {} method: handlePayByEthereum", CryptoReplenishmentServiceImpl.class);

                                CryptoReplenishmentMessage cryptoReplenishmentMessage = new CryptoReplenishmentMessage(
                                        cryptoReplenishmentHash.getUsername(),
                                        transactionDto.getFrom(),
                                        cryptoReplenishmentHash.getAmount(),
                                        new CryptoReplenishmentMessage.Exchange(
                                                coinGeckoProviderResponse.getBitcoin().getUsd(),
                                                coinGeckoProviderResponse.getEthereum().getUsd(),
                                                coinGeckoProviderResponse.getLitecoin().getUsd(),
                                                coinGeckoProviderResponse.getTron().getUsd(),
                                                coinGeckoProviderResponse.getTether().getUsd(),
                                                coinGeckoProviderResponse.getMaticNetwork().getUsd()

                                        ),
                                        cryptoReplenishmentHash.getCurrency(),
                                        cryptoReplenishmentHash.getTimestamp()
                                );

                                log.info("Deleting old payment-hash on service {} method: handlePayByEthereum",
                                        CryptoReplenishmentServiceImpl.class);

                                replenishmentHashRepository.delete(cryptoReplenishmentHash);

                                String jsonMessage = null;

                                try {
                                    jsonMessage = objectMapper.writeValueAsString(cryptoReplenishmentMessage);
                                }catch (JsonProcessingException jsonProcessingException){
                                    jsonProcessingException.printStackTrace();
                                }

                                log.info("Sending a replenishment to replenishment-queue on service {} method: handlePayByEthereum",
                                        CryptoReplenishmentServiceImpl.class);

                                rabbitTemplate.convertAndSend("replenishment-queue", jsonMessage);
                                return;
                        }
                    }

                    counter++;

                    if (counter == 12 * 4) {
                        log.warn("Payment is not found, timer is rollback on service {} method: handlePayByEthereum",
                                CryptoReplenishmentServiceImpl.class);

                        log.info("Deleting old payment-hash on service {} method: handlePayByEthereum",
                                CryptoReplenishmentServiceImpl.class);

                        replenishmentHashRepository.delete(cryptoReplenishmentHash);
                        timer.cancel();
                    }
                }
            };

            long delay = 0;
            long period = 4000;

            timer.scheduleAtFixedRate(task, delay, period);
        }).start();

        return cryptoReplenishmentResponse;
    }

    @Override
    public CryptoReplenishmentResponse handlePayByLitecoin(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest) {
        log.info("Starting method handlePayByLitecoin on service {} method: handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                if (cryptoReplenishmentRequest.amount() < 2.2){
                    log.warn("Minimal amount for LTC payment is 2.2 USD on service {} method handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for LTC payment is 2.2 USD");
                }
            }
            case EUR -> {
                if (cryptoReplenishmentRequest.amount() < 2){
                    log.warn("Minimal amount for LTC payment is 2 EUR on service {} method handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for LTC payment is 2 EUR");
                }
            }
        }

        log.info("Receiving entity User by JWT on service {} method: handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()) {
            log.warn("Incorrect JWT token on service {} method handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Checking on payments exists on service {} method: handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);

        List<CryptoReplenishmentHash> cryptoReplenishmentHashList =
                replenishmentHashRepository.findCryptoReplenishmentHashesByUsername(userDto.getUsername());

        for (CryptoReplenishmentHash key : cryptoReplenishmentHashList){
            if (key.getCurrency().equals(CryptoCurrency.LTC)){
                log.warn("Complete the previous payment before starting a new one on service {} method: handlePayByLitecoin",
                        CryptoReplenishmentServiceImpl.class);
                throw new PaymentsExistsException("Complete the previous payment before starting a new one");
            }
        }

        log.info("Receiving the cryptocurrency rate on service {} method: handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<CoinGeckoProviderResponse> coinGeckoProviderResponseResponseEntity =
                restTemplate.getForEntity(cryptoRabbit.getCoinGeckoCoinProviderURL(), CoinGeckoProviderResponse.class);

        CoinGeckoProviderResponse coinGeckoProviderResponse = coinGeckoProviderResponseResponseEntity.getBody();

        log.info("Creating a response for this method on service {} method: handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentResponse cryptoReplenishmentResponse = new CryptoReplenishmentResponse();

        cryptoReplenishmentResponse.setWalletId(cryptoRabbit.getLtcWallet());
        cryptoReplenishmentResponse.setCurrency(CryptoCurrency.LTC);
        cryptoReplenishmentResponse.setTimestamp(System.currentTimeMillis());

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getLitecoin().getUsd() ) * 1e4
                ) / 1e4);
            }
            case EUR -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getLitecoin().getEur() ) * 1e4
                ) / 1e4);
            }
        }

        log.info("Creating a new hash replenishmentHash on service {} method: handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentHash cryptoReplenishmentHash = new CryptoReplenishmentHash();

        cryptoReplenishmentHash.setId(UUID.randomUUID().toString());
        cryptoReplenishmentHash.setUsername(userDto.getUsername());
        cryptoReplenishmentHash.setWalletId(cryptoReplenishmentResponse.getWalletId());
        cryptoReplenishmentHash.setAmount(cryptoReplenishmentResponse.getAmount());
        cryptoReplenishmentHash.setCurrency(cryptoReplenishmentResponse.getCurrency());
        cryptoReplenishmentHash.setTimestamp(cryptoReplenishmentResponse.getTimestamp());

        replenishmentHashRepository.save(cryptoReplenishmentHash);

        log.info("Create a new thread to check payment status on service {} method: handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);

        new Thread(() -> {
            log.info("Start a timer to check the payment status on service {} method: handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);

            long currentTime = System.currentTimeMillis();

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int counter = 0;

                public void run() {
                    log.info("Receiving our litecoin transactions history by address on service {} method: handlePayByLitecoin",
                            CryptoReplenishmentServiceImpl.class);

                    ResponseEntity<LitecoinProviderResponse> liteCoinResponseDto = restTemplate
                            .getForEntity("https://api.tatum.io/v3/litecoin/transaction/address/"
                                    + cryptoRabbit.getLtcWallet() + "?pageSize=7&sort=desc", LitecoinProviderResponse.class);

                    for (LitecoinProviderResponse.ReplenishmentDto replenishment : liteCoinResponseDto.getBody().getReplenishments()) {
                        for (LitecoinProviderResponse.ReplenishmentDto.InputDto input : replenishment.getInputs()) {
                            if (Double.parseDouble(input.getCoin().getValue()) == cryptoReplenishmentResponse.getAmount()
                                    &&
                                    replenishment.getTime() > currentTime
                                    &&
                                    !(input.getCoin().getAddress().equals(cryptoRabbit.getLtcWallet()))
                            ) {
                                log.info("Payment was founded on service {} method: handlePayByLitecoin", CryptoReplenishmentServiceImpl.class);

                                CryptoReplenishmentMessage cryptoReplenishmentMessage = new CryptoReplenishmentMessage(
                                        cryptoReplenishmentHash.getUsername(),
                                        input.getCoin().getAddress(),
                                        cryptoReplenishmentHash.getAmount(),
                                        new CryptoReplenishmentMessage.Exchange(
                                                coinGeckoProviderResponse.getBitcoin().getUsd(),
                                                coinGeckoProviderResponse.getEthereum().getUsd(),
                                                coinGeckoProviderResponse.getLitecoin().getUsd(),
                                                coinGeckoProviderResponse.getTron().getUsd(),
                                                coinGeckoProviderResponse.getTether().getUsd(),
                                                coinGeckoProviderResponse.getMaticNetwork().getUsd()

                                        ),
                                        cryptoReplenishmentHash.getCurrency(),
                                        cryptoReplenishmentHash.getTimestamp()
                                );

                                log.info("Deleting old payment-hash on service {} method: handlePayByLitecoin",
                                        CryptoReplenishmentServiceImpl.class);

                                replenishmentHashRepository.delete(cryptoReplenishmentHash);

                                String jsonMessage = null;

                                try {
                                    jsonMessage = objectMapper.writeValueAsString(cryptoReplenishmentMessage);
                                } catch (JsonProcessingException jsonProcessingException) {
                                    jsonProcessingException.printStackTrace();
                                }

                                log.info("Sending a replenishment to replenishment-queue on service {} method: handlePayByLitecoin",
                                        CryptoReplenishmentServiceImpl.class);

                                rabbitTemplate.convertAndSend("replenishment-queue", jsonMessage);
                                return;
                            }
                        }

                        counter++;

                        if (counter == 12 * 4) {
                            log.warn("Payment is not found, timer is rollback on service {} method: handlePayByLitecoin",
                                    CryptoReplenishmentServiceImpl.class);

                            log.info("Deleting old payment-hash on service {} method: handlePayByLitecoin",
                                    CryptoReplenishmentServiceImpl.class);

                            replenishmentHashRepository.delete(cryptoReplenishmentHash);
                            timer.cancel();
                        }
                    }
                }
            };

            long delay = 0;
            long period = 4000;

            timer.scheduleAtFixedRate(task, delay, period);
        }).start();

        return cryptoReplenishmentResponse;
    }

    @Override
    public CryptoReplenishmentResponse handlePayByTron(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest) {
        log.info("Starting method handlePayByTron on service {} method: handlePayByTron", CryptoReplenishmentServiceImpl.class);

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                if (cryptoReplenishmentRequest.amount() < 1.1){
                    log.warn("Minimal amount for TRX payment is 1.1 USD on service {} method handlePayByTron", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for TRX payment is 1.1 USD");
                }
            }
            case EUR -> {
                if (cryptoReplenishmentRequest.amount() < 1){
                    log.warn("Minimal amount for TRX payment is 1 EUR on service {} method handlePayByTron", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for TRX payment is 1 EUR");
                }
            }
        }

        log.info("Receiving entity User by JWT on service {} method: handlePayByTron", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()) {
            log.warn("Incorrect JWT token on service {} method handlePayByTron", CryptoReplenishmentServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Checking on payments exists on service {} method: handlePayByTron", CryptoReplenishmentServiceImpl.class);

        List<CryptoReplenishmentHash> cryptoReplenishmentHashList =
                replenishmentHashRepository.findCryptoReplenishmentHashesByUsername(userDto.getUsername());

        for (CryptoReplenishmentHash key : cryptoReplenishmentHashList){
            if (key.getCurrency().equals(CryptoCurrency.TRX)){
                log.warn("Complete the previous payment before starting a new one on service {} method: handlePayByTron",
                        CryptoReplenishmentServiceImpl.class);
                throw new PaymentsExistsException("Complete the previous payment before starting a new one");
            }
        }

        log.info("Receiving the cryptocurrency rate on service {} method: handlePayByTron", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<CoinGeckoProviderResponse> coinGeckoProviderResponseResponseEntity =
                restTemplate.getForEntity(cryptoRabbit.getCoinGeckoCoinProviderURL(), CoinGeckoProviderResponse.class);

        CoinGeckoProviderResponse coinGeckoProviderResponse = coinGeckoProviderResponseResponseEntity.getBody();

        log.info("Creating a response for this method on service {} method: handlePayByTron", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentResponse cryptoReplenishmentResponse = new CryptoReplenishmentResponse();

        cryptoReplenishmentResponse.setWalletId(cryptoRabbit.getLtcWallet());
        cryptoReplenishmentResponse.setCurrency(CryptoCurrency.TRX);
        cryptoReplenishmentResponse.setTimestamp(System.currentTimeMillis());

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getTron().getUsd() ) * 1e2
                ) / 1e2);
            }
            case EUR -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getTron().getEur() ) * 1e2
                ) / 1e2);
            }
        }

        log.info("Creating a new hash replenishmentHash on service {} method: handlePayByTron", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentHash cryptoReplenishmentHash = new CryptoReplenishmentHash();

        cryptoReplenishmentHash.setId(UUID.randomUUID().toString());
        cryptoReplenishmentHash.setUsername(userDto.getUsername());
        cryptoReplenishmentHash.setWalletId(cryptoReplenishmentResponse.getWalletId());
        cryptoReplenishmentHash.setAmount(cryptoReplenishmentResponse.getAmount());
        cryptoReplenishmentHash.setCurrency(cryptoReplenishmentResponse.getCurrency());
        cryptoReplenishmentHash.setTimestamp(cryptoReplenishmentResponse.getTimestamp());

        replenishmentHashRepository.save(cryptoReplenishmentHash);

        log.info("Create a new thread to check payment status on service {} method: handlePayByTron", CryptoReplenishmentServiceImpl.class);

        new Thread(() -> {
            log.info("Start a timer to check the payment status on service {} method: handlePayByTron", CryptoReplenishmentServiceImpl.class);

            long currentTime = System.currentTimeMillis();

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int counter = 0;

                public void run() {
                    log.info("Receiving our tron transactions history by address on service {} method: handlePayByTron",
                            CryptoReplenishmentServiceImpl.class);

                    ResponseEntity<TronProviderResponse> tronResponseDto = restTemplate
                            .getForEntity("https://api.trongrid.io/v1/accounts/" + cryptoRabbit.getTrxWallet() + "/transactions?limit=7", TronProviderResponse.class);

                    for (TronProviderResponse.Data datum : tronResponseDto.getBody().getData()) {
                        for (TronProviderResponse.Contract contract : datum.getRaw_data().getContract()) {
                            if (contract.getParameter().getValue().getAmount()/1e6 == cryptoReplenishmentResponse.getAmount() &&
                                    datum.getRaw_data().getTimestamp() > currentTime
                                    &&
                                    !(contract.getParameter().getValue().getOwner_address().equals(cryptoRabbit.getTrxSecondWallet()))

                            ){
                                log.info("Payment was founded on service {} method: handlePayByTron", CryptoReplenishmentServiceImpl.class);

                                CryptoReplenishmentMessage cryptoReplenishmentMessage = new CryptoReplenishmentMessage(
                                        cryptoReplenishmentHash.getUsername(),
                                        contract.getParameter().getValue().getOwner_address(),
                                        cryptoReplenishmentHash.getAmount(),
                                        new CryptoReplenishmentMessage.Exchange(
                                                coinGeckoProviderResponse.getBitcoin().getUsd(),
                                                coinGeckoProviderResponse.getEthereum().getUsd(),
                                                coinGeckoProviderResponse.getLitecoin().getUsd(),
                                                coinGeckoProviderResponse.getTron().getUsd(),
                                                coinGeckoProviderResponse.getTether().getUsd(),
                                                coinGeckoProviderResponse.getMaticNetwork().getUsd()

                                        ),
                                        cryptoReplenishmentHash.getCurrency(),
                                        cryptoReplenishmentHash.getTimestamp()
                                );

                                log.info("Deleting old payment-hash on service {} method: handlePayByTron",
                                        CryptoReplenishmentServiceImpl.class);

                                replenishmentHashRepository.delete(cryptoReplenishmentHash);

                                String jsonMessage = null;

                                try {
                                    jsonMessage = objectMapper.writeValueAsString(cryptoReplenishmentMessage);
                                } catch (JsonProcessingException jsonProcessingException) {
                                    jsonProcessingException.printStackTrace();
                                }

                                log.info("Sending a replenishment to replenishment-queue on service {} method: handlePayByTron",
                                        CryptoReplenishmentServiceImpl.class);

                                rabbitTemplate.convertAndSend("replenishment-queue", jsonMessage);
                                return;
                            }
                        }

                        counter++;

                        if (counter == 12 * 4) {
                            log.warn("Payment is not found, timer is rollback on service {} method: handlePayByTron",
                                    CryptoReplenishmentServiceImpl.class);

                            log.info("Deleting old payment-hash on service {} method: handlePayByTron",
                                    CryptoReplenishmentServiceImpl.class);

                            replenishmentHashRepository.delete(cryptoReplenishmentHash);
                            timer.cancel();
                        }
                    }
                }
            };

            long delay = 0;
            long period = 4000;

            timer.scheduleAtFixedRate(task, delay, period);
        }).start();

        return cryptoReplenishmentResponse;
    }

    @Override
    public CryptoReplenishmentResponse handlePayByTetherERC20(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest) {
        log.info("Starting method handlePayByTetherERC20 on service {} method: handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                if (cryptoReplenishmentRequest.amount() < 1.2){
                    log.warn("Minimal amount for TETHER-ERC20 payment is 1.2 USD on service {} method handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for TETHER-ERC20 payment is 1.2 USD");
                }
            }
            case EUR -> {
                if (cryptoReplenishmentRequest.amount() < 1.1){
                    log.warn("Minimal amount for TETHER-ERC20 payment is 1.1 EUR on service {} method handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for TETHER-ERC20 payment is 1.1 EUR");
                }
            }
        }

        log.info("Receiving entity User by JWT on service {} method: handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()) {
            log.warn("Incorrect JWT token on service {} method handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Checking on payments exists on service {} method: handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);

        List<CryptoReplenishmentHash> cryptoReplenishmentHashList =
                replenishmentHashRepository.findCryptoReplenishmentHashesByUsername(userDto.getUsername());

        for (CryptoReplenishmentHash key : cryptoReplenishmentHashList){
            if (key.getCurrency().equals(CryptoCurrency.TETHER_ERC20)){
                log.warn("Complete the previous payment before starting a new one on service {} method: handlePayByTetherERC20",
                        CryptoReplenishmentServiceImpl.class);
                throw new PaymentsExistsException("Complete the previous payment before starting a new one");
            }
        }

        log.info("Receiving the cryptocurrency rate on service {} method: handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<CoinGeckoProviderResponse> coinGeckoProviderResponseResponseEntity =
                restTemplate.getForEntity(cryptoRabbit.getCoinGeckoCoinProviderURL(), CoinGeckoProviderResponse.class);

        CoinGeckoProviderResponse coinGeckoProviderResponse = coinGeckoProviderResponseResponseEntity.getBody();

        log.info("Creating a response for this method on service {} method: handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentResponse cryptoReplenishmentResponse = new CryptoReplenishmentResponse();

        cryptoReplenishmentResponse.setWalletId(cryptoRabbit.getLtcWallet());
        cryptoReplenishmentResponse.setCurrency(CryptoCurrency.TETHER_ERC20);
        cryptoReplenishmentResponse.setTimestamp(System.currentTimeMillis());

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getTether().getUsd() ) * 1e2
                ) / 1e2);
            }
            case EUR -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getTether().getEur() ) * 1e2
                ) / 1e2);
            }
        }

        log.info("Creating a new hash replenishmentHash on service {} method: handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentHash cryptoReplenishmentHash = new CryptoReplenishmentHash();

        cryptoReplenishmentHash.setId(UUID.randomUUID().toString());
        cryptoReplenishmentHash.setUsername(userDto.getUsername());
        cryptoReplenishmentHash.setWalletId(cryptoReplenishmentResponse.getWalletId());
        cryptoReplenishmentHash.setAmount(cryptoReplenishmentResponse.getAmount());
        cryptoReplenishmentHash.setCurrency(cryptoReplenishmentResponse.getCurrency());
        cryptoReplenishmentHash.setTimestamp(cryptoReplenishmentResponse.getTimestamp());

        replenishmentHashRepository.save(cryptoReplenishmentHash);

        log.info("Create a new thread to check payment status on service {} method: handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);

        new Thread(() -> {
            log.info("Start a timer to check the payment status on service {} method: handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);

            long currentTime = System.currentTimeMillis();

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int counter = 0;

                public void run() {
                    log.info("Receiving our Tether-ERC20 transactions history by address on service {} method: handlePayByTetherERC20",
                            CryptoReplenishmentServiceImpl.class);

                    ResponseEntity<TetherERC20ProviderResponse> tetherERC20ResponseDto = restTemplate
                            .getForEntity("https://api.etherscan.io/api?module=account&action=tokentx&contractaddress=0xdac17f958d2ee523a2206206994597c13d831ec7&"
                                            + cryptoRabbit.getEthWallet() + "&" + cryptoRabbit.getEtherScanApi() + "&page=1&offset=7&sort=desc",
                                    TetherERC20ProviderResponse.class);

                    for (TetherERC20ProviderResponse.ResultDTO resultDTO : tetherERC20ResponseDto.getBody().getResult()) {
                        if (Double.parseDouble(resultDTO.getValue()) / (10 ^ Integer.parseInt(resultDTO.getTokenDecimal())) == cryptoReplenishmentResponse.getAmount()
                                &&
                                Long.parseLong(resultDTO.getTimeStamp()) > currentTime
                                &&
                                !(resultDTO.getFrom().equals(cryptoRabbit.getTetherERC20Wallet()))
                        ) {
                            log.info("Payment was founded on service {} method: handlePayByTetherERC20", CryptoReplenishmentServiceImpl.class);

                            CryptoReplenishmentMessage cryptoReplenishmentMessage = new CryptoReplenishmentMessage(
                                    cryptoReplenishmentHash.getUsername(),
                                    resultDTO.getFrom(),
                                    cryptoReplenishmentHash.getAmount(),
                                    new CryptoReplenishmentMessage.Exchange(
                                            coinGeckoProviderResponse.getBitcoin().getUsd(),
                                            coinGeckoProviderResponse.getEthereum().getUsd(),
                                            coinGeckoProviderResponse.getLitecoin().getUsd(),
                                            coinGeckoProviderResponse.getTron().getUsd(),
                                            coinGeckoProviderResponse.getTether().getUsd(),
                                            coinGeckoProviderResponse.getMaticNetwork().getUsd()

                                    ),
                                    cryptoReplenishmentHash.getCurrency(),
                                    cryptoReplenishmentHash.getTimestamp()
                            );

                            log.info("Deleting old payment-hash on service {} method: handlePayByTetherERC20",
                                    CryptoReplenishmentServiceImpl.class);

                            replenishmentHashRepository.delete(cryptoReplenishmentHash);

                            String jsonMessage = null;

                            try {
                                jsonMessage = objectMapper.writeValueAsString(cryptoReplenishmentMessage);
                            } catch (JsonProcessingException jsonProcessingException) {
                                jsonProcessingException.printStackTrace();
                            }

                            log.info("Sending a replenishment to replenishment-queue on service {} method: handlePayByTetherERC20",
                                    CryptoReplenishmentServiceImpl.class);

                            rabbitTemplate.convertAndSend("replenishment-queue", jsonMessage);
                            return;
                        }
                    }

                    counter++;

                    if (counter == 12 * 4) {
                        log.warn("Payment is not found, timer is rollback on service {} method: handlePayByTetherERC20",
                                CryptoReplenishmentServiceImpl.class);

                        log.info("Deleting old payment-hash on service {} method: handlePayByTetherERC20",
                                CryptoReplenishmentServiceImpl.class);

                        replenishmentHashRepository.delete(cryptoReplenishmentHash);
                        timer.cancel();
                    }
                }
            };

            long delay = 0;
            long period = 4000;

            timer.scheduleAtFixedRate(task, delay, period);
        }).start();

        return cryptoReplenishmentResponse;
    }

    @Override
    public CryptoReplenishmentResponse handlePayByPolygon(String jwtToken, CryptoReplenishmentRequest cryptoReplenishmentRequest) {
        log.info("Starting method handlePayByPolygon on service {} method: handlePayByPolygon", CryptoReplenishmentServiceImpl.class);

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                if (cryptoReplenishmentRequest.amount() < 1.1){
                    log.warn("Minimal amount for MATIC payment is 1.1 USD on service {} method handlePayByPolygon", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for MATIC payment is 1.1 USD");
                }
            }
            case EUR -> {
                if (cryptoReplenishmentRequest.amount() < 1){
                    log.warn("Minimal amount for MATIC payment is 1 EUR on service {} method handlePayByPolygon", CryptoReplenishmentServiceImpl.class);
                    throw new InsufficientTopUpAmountException("Minimal amount for MATIC payment is 1 EUR");
                }
            }
        }

        log.info("Receiving entity User by JWT on service {} method: handlePayByPolygon", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()) {
            log.warn("Incorrect JWT token on service {} method handlePayByPolygon", CryptoReplenishmentServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        UserDto userDto = userDtoResponseEntity.getBody();

        log.info("Checking on payments exists on service {} method: handlePayByPolygon", CryptoReplenishmentServiceImpl.class);

        List<CryptoReplenishmentHash> cryptoReplenishmentHashList =
                replenishmentHashRepository.findCryptoReplenishmentHashesByUsername(userDto.getUsername());

        for (CryptoReplenishmentHash key : cryptoReplenishmentHashList){
            if (key.getCurrency().equals(CryptoCurrency.MATIC)){
                log.warn("Complete the previous payment before starting a new one on service {} method: handlePayByPolygon",
                        CryptoReplenishmentServiceImpl.class);
                throw new PaymentsExistsException("Complete the previous payment before starting a new one");
            }
        }

        log.info("Receiving the cryptocurrency rate on service {} method: handlePayByPolygon", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<CoinGeckoProviderResponse> coinGeckoProviderResponseResponseEntity =
                restTemplate.getForEntity(cryptoRabbit.getCoinGeckoCoinProviderURL(), CoinGeckoProviderResponse.class);

        CoinGeckoProviderResponse coinGeckoProviderResponse = coinGeckoProviderResponseResponseEntity.getBody();

        log.info("Creating a response for this method on service {} method: handlePayByPolygon", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentResponse cryptoReplenishmentResponse = new CryptoReplenishmentResponse();

        cryptoReplenishmentResponse.setWalletId(cryptoRabbit.getMaticWallet());
        cryptoReplenishmentResponse.setCurrency(CryptoCurrency.MATIC);
        cryptoReplenishmentResponse.setTimestamp(System.currentTimeMillis());

        switch (cryptoReplenishmentRequest.currency()) {
            case USD -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getMaticNetwork().getUsd() ) * 1e2
                ) / 1e2);
            }
            case EUR -> {
                cryptoReplenishmentResponse.setAmount(Math.ceil(
                        ( cryptoReplenishmentRequest.amount() / coinGeckoProviderResponse.getMaticNetwork().getEur() ) * 1e2
                ) / 1e2);
            }
        }

        log.info("Creating a new hash replenishmentHash on service {} method: handlePayByPolygon", CryptoReplenishmentServiceImpl.class);

        CryptoReplenishmentHash cryptoReplenishmentHash = new CryptoReplenishmentHash();

        cryptoReplenishmentHash.setId(UUID.randomUUID().toString());
        cryptoReplenishmentHash.setUsername(userDto.getUsername());
        cryptoReplenishmentHash.setWalletId(cryptoReplenishmentResponse.getWalletId());
        cryptoReplenishmentHash.setAmount(cryptoReplenishmentResponse.getAmount());
        cryptoReplenishmentHash.setCurrency(cryptoReplenishmentResponse.getCurrency());
        cryptoReplenishmentHash.setTimestamp(cryptoReplenishmentResponse.getTimestamp());

        replenishmentHashRepository.save(cryptoReplenishmentHash);

        log.info("Create a new thread to check payment status on service {} method: handlePayByPolygon", CryptoReplenishmentServiceImpl.class);

        new Thread(() -> {
            log.info("Start a timer to check the payment status on service {} method: handlePayByPolygon", CryptoReplenishmentServiceImpl.class);

            long currentTime = System.currentTimeMillis();

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int counter = 0;

                public void run() {
                    log.info("Receiving our Polygon transactions history by address on service {} method: handlePayByPolygon",
                            CryptoReplenishmentServiceImpl.class);

                    ResponseEntity<PolygonProviderResponse> maticResponseDto = restTemplate
                            .getForEntity("https://api.tatum.io/v3/polygon/account/transaction/"
                                    + cryptoRabbit.getMaticWallet() + "?pageSize=5&sort=desc", PolygonProviderResponse.class);

                    for (PolygonProviderResponse.ResponseDto responseDto : maticResponseDto.getBody().getResponseDtos()) {
                        if ((double) Long.parseLong(responseDto.getValue())/1e18 == cryptoReplenishmentResponse.getAmount()
                                &&
                                responseDto.getTimestamp() > currentTime
                                &&
                                !(responseDto.getFrom().equals(cryptoRabbit.getMaticWallet()))
                        ){
                            log.info("Payment was founded on service {} method: handlePayByPolygon", CryptoReplenishmentServiceImpl.class);

                            CryptoReplenishmentMessage cryptoReplenishmentMessage = new CryptoReplenishmentMessage(
                                    cryptoReplenishmentHash.getUsername(),
                                    responseDto.getFrom(),
                                    cryptoReplenishmentHash.getAmount(),
                                    new CryptoReplenishmentMessage.Exchange(
                                            coinGeckoProviderResponse.getBitcoin().getUsd(),
                                            coinGeckoProviderResponse.getEthereum().getUsd(),
                                            coinGeckoProviderResponse.getLitecoin().getUsd(),
                                            coinGeckoProviderResponse.getTron().getUsd(),
                                            coinGeckoProviderResponse.getTether().getUsd(),
                                            coinGeckoProviderResponse.getMaticNetwork().getUsd()

                                    ),
                                    cryptoReplenishmentHash.getCurrency(),
                                    cryptoReplenishmentHash.getTimestamp()
                            );

                            log.info("Deleting old payment-hash on service {} method: handlePayByPolygon",
                                    CryptoReplenishmentServiceImpl.class);

                            replenishmentHashRepository.delete(cryptoReplenishmentHash);

                            String jsonMessage = null;

                            try {
                                jsonMessage = objectMapper.writeValueAsString(cryptoReplenishmentMessage);
                            } catch (JsonProcessingException jsonProcessingException) {
                                jsonProcessingException.printStackTrace();
                            }

                            log.info("Sending a replenishment to replenishment-queue on service {} method: handlePayByPolygon",
                                    CryptoReplenishmentServiceImpl.class);

                            rabbitTemplate.convertAndSend("replenishment-queue", jsonMessage);
                            return;
                        }
                    }

                    counter++;

                    if (counter == 12 * 4) {
                        log.warn("Payment is not found, timer is rollback on service {} method: handlePayByPolygon",
                                CryptoReplenishmentServiceImpl.class);

                        log.info("Deleting old payment-hash on service {} method: handlePayByPolygon",
                                CryptoReplenishmentServiceImpl.class);

                        replenishmentHashRepository.delete(cryptoReplenishmentHash);
                        timer.cancel();
                    }
                }
            };

            long delay = 0;
            long period = 4000;

            timer.scheduleAtFixedRate(task, delay, period);
        }).start();

        return cryptoReplenishmentResponse;
    }

    @Override
    public CryptoReplenishmentHash getCryptoPayment(String jwtToken, CryptoCurrency currency) {
        log.info("Starting getCryptoPayment method on service {} method: getCryptoPayment", CryptoReplenishmentServiceImpl.class);

        log.info("Receiving entity User by JWT on service {} method: getCryptoPayment", CryptoReplenishmentServiceImpl.class);

        ResponseEntity<UserDto> userDtoResponseEntity =
                restTemplate.postForEntity(JWT_URL + "?jwtToken=" + jwtToken, null, UserDto.class);

        if (userDtoResponseEntity.getStatusCode().isError()) {
            log.warn("Incorrect JWT token on service {} method getCryptoPayment", CryptoReplenishmentServiceImpl.class);
            throw new InvalidCredentialsException("Incorrect JWT token");
        }

        log.info("Finding All Hashes by Username on service {} method: getCryptoPayment", CryptoReplenishmentServiceImpl.class);

        List<CryptoReplenishmentHash> cryptoReplenishmentHashes =
                replenishmentHashRepository.findCryptoReplenishmentHashesByUsername(userDtoResponseEntity.getBody().getUsername());

        return cryptoReplenishmentHashes.stream()
                .filter(p -> p.getCurrency().equals(currency))
                .findFirst().orElse(null);
    }
}
