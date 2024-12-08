package stanl_2.final_backend.domain.dashBoard.query.service;

import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardAdminDTO;
import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardEmployeeDTO;

import java.security.GeneralSecurityException;

public interface DashBoardQueryService {
//    DashBoardAdminDTO selectInfoForEmployee(String memberLoginId) throws GeneralSecurityException;

    DashBoardAdminDTO selectInfoForAdmin(String memberLoginId) throws GeneralSecurityException;

    DashBoardEmployeeDTO selectInfoForEmployee(String memberLoginId) throws GeneralSecurityException;
}
