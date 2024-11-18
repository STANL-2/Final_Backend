package stanl_2.final_backend.domain.certification.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.certification.command.domain.aggregate.entity.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, String> {
}
