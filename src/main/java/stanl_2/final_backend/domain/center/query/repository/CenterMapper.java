package stanl_2.final_backend.domain.center.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.center.query.dto.CenterExcelDownload;
import stanl_2.final_backend.domain.center.query.dto.CenterSearchRequestDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;

import java.util.List;

@Mapper
public interface CenterMapper {
    CenterSelectIdDTO findCenterById(String id);

    List<CenterSelectAllDTO> findCenterAll(@Param("size") int size
                                        , @Param("offset") int offset,
                                           @Param("sortField") String sortField,
                                           @Param("sortOrder") String sortOrder);

    Integer findCenterCount();

    Integer findCenterBySearchCount(@Param("centerSearchRequestDTO") CenterSearchRequestDTO centerSearchRequestDTO);

    List<CenterSelectAllDTO> findCenterBySearch(@Param("size") int size
                                                , @Param("offset") int offset
                                                , @Param("centerSearchRequestDTO") CenterSearchRequestDTO centerSearchRequestDTO,
                                                @Param("sortField") String sortField,
                                                @Param("sortOrder") String sortOrder);

    List<CenterSelectAllDTO> findCenterListBySearch(@Param("centerSearchRequestDTO") CenterSearchRequestDTO centerSearchRequestDTO);

    String findNameById(@Param("id") String id);

    List<CenterExcelDownload> findCentersForExcel();
}
