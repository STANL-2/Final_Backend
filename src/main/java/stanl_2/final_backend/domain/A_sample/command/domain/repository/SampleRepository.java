package stanl_2.final_backend.domain.A_sample.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.A_sample.command.domain.aggregate.entity.Sample;

public interface SampleRepository extends JpaRepository<Sample, String> {
}
