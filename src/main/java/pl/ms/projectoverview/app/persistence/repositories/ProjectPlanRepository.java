package pl.ms.projectoverview.app.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ms.projectoverview.app.entitites.ProjectStatus;
import pl.ms.projectoverview.app.persistence.entities.ProjectEntity;
import pl.ms.projectoverview.app.persistence.entities.ProjectPlanEntity;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface ProjectPlanRepository extends JpaRepository<ProjectPlanEntity, Integer> {
    Set<ProjectPlanEntity> findAllByUser_UserId(Integer userId);

    @Query(
            value = "select pp from ProjectPlanEntity pp join fetch pp.user u where u.userId = :userId " +
                    "and (:language is null or pp.language = :language) "
    )
    Set<ProjectPlanEntity> filterQuery(@Param("userId") Integer userId, @Param("language") String language);
}
