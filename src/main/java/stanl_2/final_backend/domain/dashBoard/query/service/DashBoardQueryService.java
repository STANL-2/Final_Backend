package stanl_2.final_backend.domain.dashBoard.query.service;

import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardAdminDTO;

public interface DashBoardQueryService {
    DashBoardAdminDTO selectInfoForEmployee(String memberLoginId);

    DashBoardAdminDTO selectInfoForAdmin(String memberLoginId);
}
