package stanl_2.final_backend.domain.career.query.service;

import stanl_2.final_backend.domain.career.query.dto.CareerDTO;

import java.util.List;

public interface CareerQueryService {
    List<CareerDTO> selectCareerList(String loginId);
}
