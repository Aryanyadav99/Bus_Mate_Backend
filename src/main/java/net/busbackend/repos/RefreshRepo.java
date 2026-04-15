package net.busbackend.repos;
import jakarta.transaction.Transactional;
import net.busbackend.entites.RefreshToken;
import net.busbackend.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshRepo extends JpaRepository<RefreshToken,String> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.id = :id")
    void deleteTokenById(@Param("id") String id);
    void deleteAllByUser(User user);
}
