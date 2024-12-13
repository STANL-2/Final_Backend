package stanl_2.final_backend.domain.notices.command.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.notices.command.domain.aggragate.entity.Notice;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,String> {
    Page<Notice> findAll(Pageable pageable);

    Optional<Notice> findByNoticeId(String noticeId);

}
