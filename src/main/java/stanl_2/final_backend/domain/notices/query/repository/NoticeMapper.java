package stanl_2.final_backend.domain.notices.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;

import java.util.List;


@Mapper
public interface NoticeMapper {
    List<NoticeDTO> findAllNotices(@Param("offset") int offset, @Param("size") int size);

    int findNoticeCount();
}

