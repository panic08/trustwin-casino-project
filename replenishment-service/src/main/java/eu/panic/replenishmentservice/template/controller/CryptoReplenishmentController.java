package eu.panic.replenishmentservice.template.controller;

import eu.panic.replenishmentservice.template.entity.Replenishment;
import eu.panic.replenishmentservice.template.enums.CryptoCurrency;
import eu.panic.replenishmentservice.template.hash.CryptoReplenishmentHash;
import eu.panic.replenishmentservice.template.payload.CryptoReplenishmentRequest;
import eu.panic.replenishmentservice.template.payload.CryptoReplenishmentResponse;
import eu.panic.replenishmentservice.template.service.implement.CryptoReplenishmentServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replenishment/crypto")
public class CryptoReplenishmentController {
    public CryptoReplenishmentController(CryptoReplenishmentServiceImpl cryptoReplenishmentService) {
        this.cryptoReplenishmentService = cryptoReplenishmentService;
    }

    private final CryptoReplenishmentServiceImpl cryptoReplenishmentService;
    @GetMapping("/getCurrentPayment")
    private CryptoReplenishmentHash getCurrentCryptoPayment(
            @RequestHeader String jwtToken,
            @RequestParam(name = "currency") CryptoCurrency currency
    ){
        return cryptoReplenishmentService.getCryptoPayment(jwtToken, currency);
    }

    @GetMapping("/getAll")
    private List<Replenishment> getReplenishmentsByUsername(@RequestHeader String jwtToken){
        return cryptoReplenishmentService.getReplenishmentsByUsername(jwtToken);
    }

    @PostMapping("/payByBtc")
    private CryptoReplenishmentResponse handlePayByBitcoin(
            @RequestHeader String jwtToken,
            @RequestBody CryptoReplenishmentRequest cryptoReplenishmentRequest
            ){
        return cryptoReplenishmentService.handlePayByBitcoin(jwtToken, cryptoReplenishmentRequest);
    }
    @PostMapping("/payByEth")
    private CryptoReplenishmentResponse handlePayEthereum(
            @RequestHeader String jwtToken,
            @RequestBody CryptoReplenishmentRequest cryptoReplenishmentRequest
    ){
        return cryptoReplenishmentService.handlePayByEthereum(jwtToken, cryptoReplenishmentRequest);
    }
    @PostMapping("/payByLtc")
    private CryptoReplenishmentResponse handlePayByLitecoin(
            @RequestHeader String jwtToken,
            @RequestBody CryptoReplenishmentRequest cryptoReplenishmentRequest
    ){
        return cryptoReplenishmentService.handlePayByLitecoin(jwtToken, cryptoReplenishmentRequest);
    }
    @PostMapping("/payByTrx")
    private CryptoReplenishmentResponse handlePayByTron(
            @RequestHeader String jwtToken,
            @RequestBody CryptoReplenishmentRequest cryptoReplenishmentRequest
    ){
        return cryptoReplenishmentService.handlePayByTron(jwtToken, cryptoReplenishmentRequest);
    }
    @PostMapping("/payByMatic")
    private CryptoReplenishmentResponse handlePayByPolygon(
            @RequestHeader String jwtToken,
            @RequestBody CryptoReplenishmentRequest cryptoReplenishmentRequest
    ){
        return cryptoReplenishmentService.handlePayByPolygon(jwtToken, cryptoReplenishmentRequest);
    }
    @PostMapping("/payByTetherERC20")
    private CryptoReplenishmentResponse handlePayByTetherERC20(
            @RequestHeader String jwtToken,
            @RequestBody CryptoReplenishmentRequest cryptoReplenishmentRequest
    ){
        return cryptoReplenishmentService.handlePayByTetherERC20(jwtToken, cryptoReplenishmentRequest);
    }

}
