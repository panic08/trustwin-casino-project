package eu.panic.gamejackpotservice.template.repository;

public interface UserRepository {
    void updateBalanceById(long balance, long id);
    long findBalanceById(long id);
}
