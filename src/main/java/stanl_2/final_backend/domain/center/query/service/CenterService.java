package stanl_2.final_backend.domain.center.query.service;

import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;

public interface CenterService {
    CenterSelectIdDTO selectByCenterId(Long centerId);
}
