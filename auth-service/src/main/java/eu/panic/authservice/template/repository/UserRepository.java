package eu.panic.authservice.template.repository;

import eu.panic.authservice.template.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByRefData_RefLink(String refLinK);
    @Modifying
    @Query("UPDATE User u SET u.data.clientSeed = :clientSeed WHERE u.username = :username")
    void updateClientSeedByUsername(@Param("clientSeed") String clientSeed, @Param("username") String username);
    @Modifying
    @Query("UPDATE User u SET u.data.serverSeed = :serverSeed WHERE u.username = :username")
    void updateServerSeedByUsername(@Param("serverSeed") String serverSeed, @Param("username") String username);
    User findUserByEmail(String email);
    boolean existsByIpAddress(String ipAddress);
    @Modifying
    @Query("UPDATE User u SET u.personalData = :personalData WHERE u.username = :username")
    void updatePersonalDataByUsername(@Param("personalData") User.PersonalData personalData, @Param("username") String username);
}
