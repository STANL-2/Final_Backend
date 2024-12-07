package stanl_2.final_backend.domain.dashBoard.query.service;

import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardDTO;

public interface DashBoardQueryService {
    DashBoardDTO selectAllInfo(String memberLoginId);
}
