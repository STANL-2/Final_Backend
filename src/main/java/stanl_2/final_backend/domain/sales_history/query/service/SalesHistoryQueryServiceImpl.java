package stanl_2.final_backend.domain.sales_history.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryCommonException;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryErrorCode;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectDTO;
import stanl_2.final_backend.domain.sales_history.query.repository.SalesHistoryMapper;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class SalesHistoryQueryServiceImpl implements SalesHistoryQueryService {

    private final SalesHistoryMapper salesHistoryMapper;
    private final AESUtils aesUtils;
    private final MemberQueryService memberQueryService;
    private final AuthQueryService authQueryService;

    @Autowired
    public SalesHistoryQueryServiceImpl(SalesHistoryMapper salesHistoryMapper, AESUtils aesUtils, MemberQueryService memberQueryService, @Qualifier("authQueryService") AuthQueryService authQueryService) {
        this.salesHistoryMapper = salesHistoryMapper;
        this.aesUtils = aesUtils;
        this.memberQueryService = memberQueryService;
        this.authQueryService = authQueryService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesHistorySelectDTO> selectAllSalesHistory(SalesHistorySelectDTO salesHistorySelectDTO, Pageable pageable) throws GeneralSecurityException {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        if(salesHistorySelectDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_MANAGER".equals(role.getAuthority())
                        || "ROLE_REPRESENTATIVE".equals(role.getAuthority()))
                ){

            List<SalesHistorySelectDTO> salesHistoryList = salesHistoryMapper.findAllSalesHistory(size,offset);

            int total = salesHistoryMapper.findSalesHistoryCount();

            if(salesHistoryList.isEmpty() || total == 0){
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
            }
            return new PageImpl<>(salesHistoryList, pageable, total);
        }else if(salesHistorySelectDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_MEMBER".equals(role.getAuthority()))) {

            String searcherId = authQueryService.selectMemberIdByLoginId(salesHistorySelectDTO.getSearcherName());

            List<SalesHistorySelectDTO> salesHistoryList = salesHistoryMapper.findSalesHistoryByMember(size,offset, searcherId);

            int total = salesHistoryMapper.findSalesHistoryCountByMember(searcherId);

            if(salesHistoryList.isEmpty() || total == 0){
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.SALES_HISTORY_NOT_FOUND);
            }
            return new PageImpl<>(salesHistoryList, pageable, total);

        }else{
            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }

    }


}
