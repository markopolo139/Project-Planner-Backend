package pl.ms.projectoverview.app.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.ms.projectoverview.app.persistence.entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    @Query("select u from UserEntity u left join fetch u.projects left join u.projectPlans where u.userId = :userId")
    Optional<UserEntity> fetchUserWithProjects(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    void deleteByEmail(String email);
}
