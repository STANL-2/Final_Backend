package stanl_2.final_backend.domain.dashBoard.query.repository;

import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardDTO;

public interface DashBoardMapper {
    DashBoardDTO findDashBoardInfoByMemberId(String memberId);
}
