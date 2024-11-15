package stanl_2.final_backend.domain.order.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectAllDTO;

import java.util.Map;

public interface OrderQueryService {
    Page<OrderSelectAllDTO> selectAll(String memberId, Pageable pageable);
}
