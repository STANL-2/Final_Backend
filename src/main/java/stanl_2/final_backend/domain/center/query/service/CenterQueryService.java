package stanl_2.final_backend.domain.center.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.center.query.dto.CenterSearchRequestDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;

import java.util.List;

public interface CenterQueryService {
    CenterSelectIdDTO selectByCenterId(String id);

    Page<CenterSelectAllDTO> selectAll(Pageable pageable);

    Page<CenterSelectAllDTO> selectBySearch(CenterSearchRequestDTO centerSearchRequestDTO, Pageable pageable);

    List<CenterSelectAllDTO> selectCenterListBySearch(CenterSearchRequestDTO centerSearchRequestDTO);

    String selectNameById(String id);

    void exportCenterToExcel(HttpServletResponse response);
}
