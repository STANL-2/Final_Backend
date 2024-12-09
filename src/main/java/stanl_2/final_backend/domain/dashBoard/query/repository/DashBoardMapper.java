package stanl_2.final_backend.domain.dashBoard.query.repository;

import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardAdminDTO;

public interface DashBoardMapper {
    DashBoardAdminDTO findDashBoardInfoByMemberId(String memberId);
}
