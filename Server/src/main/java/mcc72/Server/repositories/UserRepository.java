package mcc72.Server.repositories;

import mcc72.Server.models.entity.Role;
import mcc72.Server.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUsername(String username);

    @Modifying
    @Query("update User u set u.failedAttempt = ?1 where u.id = ?2")
    Integer setFailedAttemptForUser(Integer failedAttempt, Integer id);

    @Query("select u from User u where u.userRole = ?1")
    List<User> findByUserRole(Role userRole);
}
