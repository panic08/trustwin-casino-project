package eu.panic.managementgameservice.template.service.implement;

import eu.panic.managementgameservice.template.dto.UserDto;
import eu.panic.managementgameservice.template.entity.Game;
import eu.panic.managementgameservice.template.entity.Replenishment;
import eu.panic.managementgameservice.template.entity.User;
import eu.panic.managementgameservice.template.enums.Rank;
import eu.panic.managementgameservice.template.payload.GameMessage;
import eu.panic.managementgameservice.template.repository.implement.GameRepositoryImpl;
import eu.panic.managementgameservice.template.repository.implement.ReplenishmentRepositoryImpl;
import eu.panic.managementgameservice.template.repository.implement.UserRepositoryImpl;
import eu.panic.managementgameservice.template.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GameServiceImpl implements GameService {
    public GameServiceImpl(GameRepositoryImpl gameRepository, ReplenishmentRepositoryImpl replenishmentRepository,
                           UserRepositoryImpl userRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameRepository = gameRepository;
        this.replenishmentRepository = replenishmentRepository;
        this.userRepository = userRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    private final GameRepositoryImpl gameRepository;
    private final ReplenishmentRepositoryImpl replenishmentRepository;
    private final UserRepositoryImpl userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void handleGameMessage(GameMessage gameMessage) {
        log.info("Starting method handleGameMessage on service {} method: handleGameMessage", GameServiceImpl.class);

        log.info("Finding replenishmentList entities by Username on service {} method: handleGameMessage", GameServiceImpl.class);

        List<Replenishment> replenishmentList = replenishmentRepository.findAllByUsername(gameMessage.getUser().getUsername());

        log.info("Finding gameList entities by Username on service {} method: handleGameMessage", GameServiceImpl.class);

        List<Game> gameList  = gameRepository.findAllByUsername(gameMessage.getUser().getUsername());

        long replenishmentListSize = replenishmentList.size();
        long gameListSize = gameList.size();

        Rank tempRank;

        log.info("Updating entity balance by Username on service {} method: handleGameMessage", GameServiceImpl.class);

        if (gameListSize  >= 1_000_000_000 && replenishmentListSize >= 100_000_000){
            tempRank = Rank.DIVINE;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.10), gameMessage.getUser().getId());
            }
        }else if (gameListSize  >= 100_000_000 && replenishmentListSize >= 10_000_000){
            tempRank = Rank.GODLIKE;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.08), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 60_000_000 && replenishmentListSize >= 6_000_000){
            tempRank = Rank.IMMORTAL;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.08), gameMessage.getUser().getId());
            }
        }else if (gameListSize>= 30_000_000 && replenishmentListSize >= 3_000_000){
            tempRank = Rank.INTERNATIONAL;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.08), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 10_000_000 && replenishmentListSize >= 1_000_000){
            tempRank = Rank.LEGEND;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.07), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 6_000_000 && replenishmentListSize >= 600_000){
            tempRank = Rank.SUPERIOR;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.07), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 3_000_000 && replenishmentListSize >= 300_000){
            tempRank = Rank.DIAMOND;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.07), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 1_500_000 && replenishmentListSize >= 150_000){
            tempRank = Rank.DRAKE;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.05), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 750_000 && replenishmentListSize >= 75_000){
            tempRank = Rank.CASH_MACHINE;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.05), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 250_000 && replenishmentListSize >= 25_000){
            tempRank = Rank.GRANDMASTER;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.05), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 100_000 && replenishmentListSize >= 10_000){
            tempRank  = Rank.MASTER;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.05), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 50_000 && replenishmentListSize >= 5_000){
            tempRank = Rank.SHARK;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.03), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 20_000 && replenishmentListSize >= 2_000){
            tempRank = Rank.PRO;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.03), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 10_000 && replenishmentListSize >= 1_000){
            tempRank = Rank.GAMBLING;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.03), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 5_000 && replenishmentListSize >= 500){
            tempRank = Rank.AMATEUR;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.02), gameMessage.getUser().getId());
            }
        }else if (gameListSize >= 2_000 && replenishmentListSize >= 50){
            tempRank = Rank.BEGINNER;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.02), gameMessage.getUser().getId());
            }
        }else {
            tempRank = Rank.NEWBIE;

            if (gameMessage.getWin() == 0){
                userRepository.updateBalanceById(gameMessage.getUser().getBalance() + (long) (gameMessage.getBet() * 0.02), gameMessage.getUser().getId());
            }
        }

        if (!gameMessage.getUser().getData().getRank().equals(tempRank)){
            userRepository.updateDataRankById(tempRank, gameMessage.getUser().getId());
        }

        simpMessagingTemplate.convertAndSend("/games/topic", gameMessage);
    }

    @Override
    public List<GameMessage> getAllGameMessages() {
        log.info("Starting method getAllGameMessages on service {} method: getAllGameMessages", GameServiceImpl.class);

        log.info("Finding last ten entities gameList on service {} method: getAllGameMessages", GameServiceImpl.class);

        List<Game> gameList =  gameRepository.findLastTen();

        List<GameMessage> gameMessageList = new ArrayList<>();

        for(Game game : gameList){
            GameMessage gameMessage = new GameMessage();

            gameMessage.setGameType(game.getGameType());
            gameMessage.setUser(userRepository.findByUsername(game.getUsername()));
            gameMessage.setBet(game.getBet());
            gameMessage.setWin(game.getWin());
            gameMessage.setCoefficient(game.getCoefficient());
            gameMessage.setTimestamp(game.getTimestamp());

            gameMessageList.add(gameMessage);
        }

        return gameMessageList;
    }
}
