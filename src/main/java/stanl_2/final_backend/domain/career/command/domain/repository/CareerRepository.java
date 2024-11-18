package stanl_2.final_backend.domain.career.command.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.career.command.domain.aggregate.entity.Career;

@Repository
public interface CareerRepository extends JpaRepository<Career, String> {
}
