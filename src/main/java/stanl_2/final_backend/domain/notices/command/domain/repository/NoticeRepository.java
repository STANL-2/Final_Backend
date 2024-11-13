package stanl_2.final_backend.domain.notices.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.notices.command.domain.aggragate.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice,String> {
}
