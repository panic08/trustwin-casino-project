package eu.panic.managementgameservice.template.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.panic.managementgameservice.template.payload.GameMessage;
import eu.panic.managementgameservice.template.service.implement.GameServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "game-queue")
public class GameRabbitListener {
    public GameRabbitListener(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    private final GameServiceImpl gameService;
    @RabbitHandler
    public void listenGames(String gameMessage){
        ObjectMapper  objectMapper = new ObjectMapper();

        GameMessage gameMessage1 = null;
        gameMessage1 = objectMapper.convertValue(gameMessage, GameMessage.class);

        gameService.handleGameMessage(gameMessage1);
    }
}
