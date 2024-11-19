package stanl_2.final_backend.domain.education.query.service;

import stanl_2.final_backend.domain.education.query.dto.EducationDTO;

import java.util.List;

public interface EducationQueryService {
    List<EducationDTO> selectEducationList(String loginId);
}
