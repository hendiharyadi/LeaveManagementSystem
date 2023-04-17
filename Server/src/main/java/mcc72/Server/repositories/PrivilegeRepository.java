package mcc72.Server.repositories;

import mcc72.Server.models.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

    public Optional<Privilege> findByName (String name);
}
