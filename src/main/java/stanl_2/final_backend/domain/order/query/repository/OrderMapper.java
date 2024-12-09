package stanl_2.final_backend.domain.order.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.order.query.dto.OrderExcelDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectAllDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectIdDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectSearchDTO;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<OrderSelectSearchDTO> findSearchOrderByMemberId(@Param("offset") int offset,
                                                         @Param("pageSize") int pageSize,
                                                         @Param("orderSelectSearchDTO") OrderSelectSearchDTO orderSelectSearchDTO,
                                                         @Param("sortField") String sortField,
                                                         @Param("sortOrder") String sortOrder);

    int findOrderCountByMemberId(@Param("memberId")  String memberId);

    OrderSelectIdDTO findOrderByIdAndMemberId(String orderId, String memberId);

    List<OrderSelectAllDTO> findAllOrderByMemberId(@Param("offset") int offset,
                                                   @Param("pageSize") int pageSize,
                                                   @Param("memberId") String memberId,
                                                   @Param("sortField") String sortField,
                                                   @Param("sortOrder") String sortOrder);

    int findOrderSearchCountByMemberId(OrderSelectSearchDTO orderSelectSearchDTO);

    List<OrderSelectAllDTO> findAllOrder(@Param("offset") int offset,
                                         @Param("pageSize") int pageSize);

    int findOrderCount();

    OrderSelectIdDTO findOrderByOrderId(String orderId);

    List<OrderSelectSearchDTO> findSearchOrder(@Param("offset") int offset,
                                               @Param("pageSize") int pageSize,
                                               @Param("orderSelectSearchDTO") OrderSelectSearchDTO orderSelectSearchDTO,
                                               @Param("sortField") String sortField,
                                               @Param("sortOrder") String sortOrder);

    int findOrderSearchCount(OrderSelectSearchDTO orderSelectSearchDTO);

    List<OrderExcelDTO> findOrderForExcel();

    String selectByContractId(String orderId);

    List<OrderSelectAllDTO> findAllOrderCenter(@Param("offset") int offset,
                                               @Param("pageSize") int pageSize,
                                               @Param("memberId") String memberId1,
                                               @Param("centerId") String centerId);

    Integer findOrderCountCenter();

    List<OrderSelectSearchDTO> findSearchOrderCenter(@Param("offset") int offset,
                                                     @Param("pageSize") int pageSize,
                                                     @Param("orderSelectSearchDTO") OrderSelectSearchDTO orderSelectSearchDTO,
                                                     @Param("sortField") String sortField,
                                                     @Param("sortOrder") String sortOrder);

    Integer findOrderSearchCountCenter(OrderSelectSearchDTO orderSelectSearchDTO);
}
