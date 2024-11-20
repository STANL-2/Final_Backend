package stanl_2.final_backend.domain.organization.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.organization.command.domain.aggregate.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {
}
