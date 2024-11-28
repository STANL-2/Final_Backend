package stanl_2.final_backend.domain.s3.command.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.s3.command.domain.domain.aggregate.File;

public interface FileRepository extends JpaRepository<File,String> {
}
