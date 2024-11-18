package stanl_2.final_backend.domain.family.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.family.command.domain.aggregate.entity.Family;

public interface FamilyRepository extends JpaRepository<Family, String> {
}
