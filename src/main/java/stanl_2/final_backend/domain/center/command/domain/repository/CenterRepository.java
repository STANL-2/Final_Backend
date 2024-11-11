package stanl_2.final_backend.domain.center.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.center.command.domain.aggregate.entity.Center;

public interface CenterRepository extends JpaRepository<Center,String> {

}
