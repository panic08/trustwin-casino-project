package eu.panic.managementreplenishmentservice.template.service.implement;

import eu.panic.managementreplenishmentservice.template.entity.Replenishment;
import eu.panic.managementreplenishmentservice.template.entity.User;
import eu.panic.managementreplenishmentservice.template.enums.PaymentMethod;
import eu.panic.managementreplenishmentservice.template.payload.CryptoReplenishmentMessage;
import eu.panic.managementreplenishmentservice.template.repository.ReplenishmentRepository;
import eu.panic.managementreplenishmentservice.template.repository.UserRepository;
import eu.panic.managementreplenishmentservice.template.service.CryptoReplenishmentService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CryptoReplenishmentServiceImpl implements CryptoReplenishmentService {
    public CryptoReplenishmentServiceImpl(UserRepository userRepository, ReplenishmentRepository replenishmentRepository) {
        this.userRepository = userRepository;
        this.replenishmentRepository = replenishmentRepository;
    }

    private final UserRepository userRepository;
    private final ReplenishmentRepository replenishmentRepository;
    @Override
    @Transactional(rollbackOn = Throwable.class)
    public void handleReplenishment(CryptoReplenishmentMessage cryptoReplenishmentMessage) {
        log.info("Starting method handleReplenishment on service {} method: handleReplenishment", CryptoReplenishmentServiceImpl.class);

        log.info("Creating entity replenishment on service {} method: handleReplenishment", CryptoReplenishmentServiceImpl.class);

        Replenishment replenishment = new Replenishment();

        replenishment.setUsername(cryptoReplenishmentMessage.username());
        replenishment.setWalletId(cryptoReplenishmentMessage.walletId());
        replenishment.setAmount(cryptoReplenishmentMessage.amount());
        replenishment.setTimestamp(cryptoReplenishmentMessage.timestamp());

        log.info("Finding entity user by Username on service {} method: handleReplenishment", CryptoReplenishmentServiceImpl.class);

        User user = userRepository.findUserByUsername(cryptoReplenishmentMessage.username());

        long newBalance = user.getBalance();

        switch (cryptoReplenishmentMessage.currency()){
            case BTC -> {
                user.setBalance(newBalance + (long) (cryptoReplenishmentMessage.amount() * (cryptoReplenishmentMessage.exchange().getBtcExchange() / 0.01)));
                replenishment.setPaymentMethod(PaymentMethod.BTC);
            }

            case ETH -> {
                user.setBalance(newBalance + (long) (cryptoReplenishmentMessage.amount() * (cryptoReplenishmentMessage.exchange().getEthExchange() / 0.01)));
                replenishment.setPaymentMethod(PaymentMethod.ETH);
            }

            case LTC -> {
                user.setBalance(newBalance + (long) (cryptoReplenishmentMessage.amount() * (cryptoReplenishmentMessage.exchange().getLtcExchange() / 0.01)));
                replenishment.setPaymentMethod(PaymentMethod.LTC);
            }

            case TRX -> {
                user.setBalance(newBalance + (long) (cryptoReplenishmentMessage.amount() * (cryptoReplenishmentMessage.exchange().getTrxExchange() / 0.01)));
                replenishment.setPaymentMethod(PaymentMethod.TRX);
            }

            case MATIC -> {
                user.setBalance(newBalance + (long) (cryptoReplenishmentMessage.amount() * (cryptoReplenishmentMessage.exchange().getMaticExchange() / 0.01)));
                replenishment.setPaymentMethod(PaymentMethod.MATIC);
            }

            case TETHER_ERC20 -> {
                user.setBalance(newBalance + (long) (cryptoReplenishmentMessage.amount() * (cryptoReplenishmentMessage.exchange().getTetherERC20Exchange() / 0.01)));
                replenishment.setPaymentMethod(PaymentMethod.TETHER_ERC20);
            }
        }
        log.info("Saving an entity replenishment on service {} method: handleReplenishment", CryptoReplenishmentServiceImpl.class);

        replenishmentRepository.save(replenishment);

        log.info("Saving an entity user on service {} method: handleReplenishment", CryptoReplenishmentServiceImpl.class);

        userRepository.save(user);

        log.info("Saving an entity replenishment on service {} method: handleReplenishment", CryptoReplenishmentServiceImpl.class);

        log.info("Update referral entity data on service {} method: handleReplenishment", CryptoReplenishmentServiceImpl.class);

        if (user.getRefData().getInvitedBy() != null) {
            User user1 = userRepository.findUserByRefData_RefLink(user.getRefData().getInvitedBy());

            double percent = 0;

            switch (user1.getRefData().getLevel()) {
                case 1 -> percent = 0.02;
                case 2 -> percent = 0.03;
                case 3 -> percent = 0.04;
                case 4 -> percent = 0.05;
                case 5 -> percent = 0.06;
            }

            user1.setBalance(
                    user1.getBalance() + (long) (cryptoReplenishmentMessage.amount() * (cryptoReplenishmentMessage.exchange().getTetherERC20Exchange() / 0.01) * percent)
            );

            User.RefData refData1 = user1.getRefData();

            refData1.setEarned(
                    refData1.getEarned() + (long) (cryptoReplenishmentMessage.amount() * (cryptoReplenishmentMessage.exchange().getTetherERC20Exchange() / 0.01) * percent)
            );

            user1.setRefData(refData1);

            log.info("Saving an entity user1 on service {} method: handleReplenishment", CryptoReplenishmentServiceImpl.class);

            userRepository.save(user1);
        }
    }
}
