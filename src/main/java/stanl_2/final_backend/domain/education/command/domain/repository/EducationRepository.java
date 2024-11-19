package stanl_2.final_backend.domain.education.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.education.command.domain.aggregate.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, String> {
}
