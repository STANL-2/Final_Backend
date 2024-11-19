package stanl_2.final_backend.domain.family.query.service;

import stanl_2.final_backend.domain.family.query.dto.FamilyDTO;

import java.util.List;

public interface FamilyQueryService {
    List<FamilyDTO> selectFamilyList(String loginId);
}
