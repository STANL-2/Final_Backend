package stanl_2.final_backend.domain.order.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectAllDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectIdDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectSearchDTO;

import java.security.GeneralSecurityException;

public interface OrderQueryService {
    Page<OrderSelectAllDTO> selectAllEmployee(String loginId, Pageable pageable);

    OrderSelectIdDTO selectDetailOrderEmployee(OrderSelectIdDTO orderSelectIdDTO);

    Page<OrderSelectSearchDTO> selectSearchOrdersEmployee(OrderSelectSearchDTO orderSelectSearchDTO, Pageable pageable) throws GeneralSecurityException;

    Page<OrderSelectAllDTO> selectAll(Pageable pageable);

    OrderSelectIdDTO selectDetailOrder(OrderSelectIdDTO orderSelectIdDTO);

    Page<OrderSelectSearchDTO> selectSearchOrders(OrderSelectSearchDTO orderSelectSearchDTO, Pageable pageable) throws GeneralSecurityException;

    String selectByContractId(String orderId);

    void exportOrder(HttpServletResponse response);

    Page<OrderSelectAllDTO> selectAllCenter(Pageable pageable, String memberId) throws GeneralSecurityException;

    Page<OrderSelectSearchDTO> selectSearchOrdersCenter(OrderSelectSearchDTO orderSelectSearchDTO, Pageable pageable) throws GeneralSecurityException;
}
