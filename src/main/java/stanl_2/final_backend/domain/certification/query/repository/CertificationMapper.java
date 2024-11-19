package stanl_2.final_backend.domain.certification.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.certification.query.dto.CertificationDTO;

import java.util.List;

@Mapper
public interface CertificationMapper {
    List<CertificationDTO> selectCertificationInfo(@Param("memberId") String memberId);
}
