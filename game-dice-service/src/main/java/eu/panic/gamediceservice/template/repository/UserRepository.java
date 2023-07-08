package eu.panic.gamediceservice.template.repository;

public interface UserRepository {
    void updateBalanceById(long balance, long id);
    void updateClientSeedAndServerSeedById(String clientSeed, String serverSeed, long id);
}
