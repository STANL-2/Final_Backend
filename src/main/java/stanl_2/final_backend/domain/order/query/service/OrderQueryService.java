package stanl_2.final_backend.domain.order.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectAllDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectIdDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectSearchDTO;

import java.security.GeneralSecurityException;
import java.util.Map;

public interface OrderQueryService {
    Page<OrderSelectAllDTO> selectAllEmployee(String loginId, Pageable pageable);

    OrderSelectIdDTO selectDetailOrderEmployee(OrderSelectIdDTO orderSelectIdDTO);

    Page<OrderSelectSearchDTO> selectSearchOrdersEmployee(OrderSelectSearchDTO orderSelectSearchDTO, Pageable pageable);

    Page<OrderSelectAllDTO> selectAll(Pageable pageable);

    OrderSelectIdDTO selectDetailOrder(OrderSelectIdDTO orderSelectIdDTO);

    Page<OrderSelectSearchDTO> selectSearchOrders(OrderSelectSearchDTO orderSelectSearchDTO, Pageable pageable) throws GeneralSecurityException;

    void exportOrder(HttpServletResponse response);
}
