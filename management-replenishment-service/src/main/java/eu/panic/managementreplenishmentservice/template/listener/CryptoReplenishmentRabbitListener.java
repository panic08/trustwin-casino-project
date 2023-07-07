package eu.panic.managementreplenishmentservice.template.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.panic.managementreplenishmentservice.template.payload.CryptoReplenishmentMessage;
import eu.panic.managementreplenishmentservice.template.service.implement.CryptoReplenishmentServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "replenishment-queue")
public class CryptoReplenishmentRabbitListener {
    public CryptoReplenishmentRabbitListener(CryptoReplenishmentServiceImpl cryptoReplenishmentService) {
        this.cryptoReplenishmentService = cryptoReplenishmentService;
    }

    private final CryptoReplenishmentServiceImpl cryptoReplenishmentService;
    @RabbitHandler
    public void receiveCryptoReplenishmentMessage(String cryptoReplenishmentMessage){
        ObjectMapper objectMapper = new ObjectMapper();

        CryptoReplenishmentMessage cryptoReplenishmentMessage1 = null;
        try {
            cryptoReplenishmentMessage1 = objectMapper.readValue(cryptoReplenishmentMessage, CryptoReplenishmentMessage.class);
        } catch (JsonProcessingException jsonProcessingException){
            jsonProcessingException.printStackTrace();
        }

        cryptoReplenishmentService.handleReplenishment(cryptoReplenishmentMessage1);
    }
}
