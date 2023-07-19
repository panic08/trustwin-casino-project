package eu.panic.gamecrashservice.template.repository;

public interface UserRepository {
    void updateBalanceById(long balance, long id);
}
