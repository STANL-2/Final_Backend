package stanl_2.final_backend.domain.center.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;

import java.util.List;
import java.util.Map;

@Service
public interface CenterService {
    CenterSelectIdDTO selectByCenterId(String id);

    Page<Map<String, Object>> selectAll(Pageable pageable);
}
