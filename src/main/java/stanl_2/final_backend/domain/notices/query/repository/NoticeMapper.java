package stanl_2.final_backend.domain.notices.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleExcelDownload;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.dto.NoticeExcelDownload;
import stanl_2.final_backend.domain.notices.query.dto.SearchDTO;

import java.util.List;


@Mapper
public interface NoticeMapper {
    List<NoticeDTO> findAllNotices(@Param("offset") int offset, @Param("size") int size);

    int findNoticeCount();

    List<NoticeDTO> findNotices(
            @Param("offset") int offset,
            @Param("size") int size,
            @Param("searchDTO") SearchDTO searchDTO
    );

//    int findNoticesCount(@Param("searchDTO") SearchDTO searchDTO);
    NoticeDTO findNotice(@Param("noticeId") String noticeId);

    List<NoticeExcelDownload> findNoticesForExcel();
}



