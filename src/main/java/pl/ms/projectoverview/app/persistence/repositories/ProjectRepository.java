package pl.ms.projectoverview.app.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.ms.projectoverview.app.entitites.ProjectStatus;
import pl.ms.projectoverview.app.persistence.entities.ProjectEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

    List<ProjectEntity> findAllByUser_UserId(Integer userId);

    @Query("select p from ProjectEntity p left join p.user u where u.notificationToken != null and p.deadline != null ")
    List<ProjectEntity> findAllForNotification();

    @Query(
            value = "select p from ProjectEntity p join fetch p.user u where u.userId = :userId " +
                    "and (:language is null or p.language = :language) " +
                    "and (:startDateBegin is null or p.dateOfStart >= :startDateBegin) " +
                    "and (:startDateEnd is null or p.dateOfStart <= :startDateEnd) " +
                    "and (:current is null or p.isCurrentProject = :current) " +
                    "and (:status is null or p.projectStatus = :status)"
    )
    List<ProjectEntity> filterQuery(
            @Param("userId") Integer userId, @Param("language") String language,
            @Param("startDateBegin") LocalDateTime dateOfStartBeginning, @Param("startDateEnd") LocalDateTime dateOfStartEnding,
            @Param("current") Boolean isCurrentProject, @Param("status") ProjectStatus projectStatus
    );
}
