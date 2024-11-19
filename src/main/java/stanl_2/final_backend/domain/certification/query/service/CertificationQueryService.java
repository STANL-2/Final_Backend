package stanl_2.final_backend.domain.certification.query.service;

import stanl_2.final_backend.domain.certification.query.dto.CertificationDTO;

import java.util.List;

public interface CertificationQueryService {
    List<CertificationDTO> selectCertificationList(String loginId);
}
