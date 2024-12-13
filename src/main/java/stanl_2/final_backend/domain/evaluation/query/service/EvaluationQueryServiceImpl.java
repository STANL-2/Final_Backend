package stanl_2.final_backend.domain.evaluation.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.center.query.service.CenterQueryService;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationCommonException;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationErrorCode;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationExcelDownload;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationSearchDTO;
import stanl_2.final_backend.domain.evaluation.query.repository.EvaluationMapper;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationQueryServiceImpl implements EvaluationQueryService {

    private final EvaluationMapper evaluationMapper;
    private final MemberQueryService memberQueryService;
    private final AuthQueryService authQueryService;
    private final CenterQueryService centerQueryService;
    private final ExcelUtilsV1 excelUtilsV1;

    @Autowired
    public EvaluationQueryServiceImpl(EvaluationMapper evaluationMapper, MemberQueryService memberQueryService, AuthQueryService authQueryService, CenterQueryService centerQueryService, ExcelUtilsV1 excelUtilsV1) {
        this.evaluationMapper = evaluationMapper;
        this.memberQueryService = memberQueryService;
        this.authQueryService = authQueryService;
        this.centerQueryService = centerQueryService;
        this.excelUtilsV1 = excelUtilsV1;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> selectAllEvaluationsByManager(EvaluationDTO evaluationDTO, Pageable pageable) throws GeneralSecurityException {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        MemberDTO memberDTO = memberQueryService.selectMemberInfo(evaluationDTO.getMemberId());
        String centerId = memberDTO.getCenterId();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;

        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<EvaluationDTO> evaluationList = evaluationMapper.findEvaluationByCenterId(size,offset, centerId, sortField, sortOrder);

        int total = evaluationMapper.findEvaluationCountByCenterId(centerId);

        if(evaluationList.isEmpty() || total == 0) {
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }
        evaluationList.forEach(evaluation -> {
            try {
                evaluation.setMemberId(memberQueryService.selectNameById(evaluation.getMemberId()));
                evaluation.setWriterId(memberQueryService.selectNameById(evaluation.getWriterId()));
                evaluation.setCenterId(centerQueryService.selectNameById(evaluation.getCenterId()));
            } catch (Exception e) {
                throw new EvaluationCommonException(EvaluationErrorCode.MEMBER_CENTER_NOT_FOUND);
            }
        });
        return new PageImpl<>(evaluationList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> selectAllEvaluationsByRepresentative(EvaluationDTO evaluationDTO, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;

        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }


        List<EvaluationDTO> evaluationList = evaluationMapper.findAllEvaluations(size,offset, sortField, sortOrder);

        int total = evaluationMapper.findEvaluationCount();

        if(evaluationList.isEmpty() || total == 0) {
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }

        evaluationList.forEach(evaluation -> {
            try {
                evaluation.setMemberId(memberQueryService.selectNameById(evaluation.getMemberId()));
                evaluation.setWriterId(memberQueryService.selectNameById(evaluation.getWriterId()));
                evaluation.setCenterId(centerQueryService.selectNameById(evaluation.getCenterId()));
            } catch (Exception e) {
                throw new EvaluationCommonException(EvaluationErrorCode.MEMBER_CENTER_NOT_FOUND);
            }
        });

        return new PageImpl<>(evaluationList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public EvaluationDTO selectEvaluationById(String id) {

        EvaluationDTO evaluationDTO = evaluationMapper.findEvaluationById(id);

        if(evaluationDTO == null){
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }

        try {
            evaluationDTO.setMemberId(memberQueryService.selectNameById(evaluationDTO.getMemberId()));
            evaluationDTO.setWriterId(memberQueryService.selectNameById(evaluationDTO.getWriterId()));
            evaluationDTO.setCenterId(centerQueryService.selectNameById(evaluationDTO.getCenterId()));
        } catch (Exception e) {
            throw new EvaluationCommonException(EvaluationErrorCode.MEMBER_CENTER_NOT_FOUND);
        }

        return evaluationDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> selectEvaluationBySearchByManager(Pageable pageable, EvaluationSearchDTO evaluationSearchDTO) throws GeneralSecurityException {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        String writerName = Optional.ofNullable(evaluationSearchDTO.getWriterName()).orElse("");
        if (!writerName.isEmpty()) {
            evaluationSearchDTO.setWriterName(authQueryService.selectMemberIdByLoginId(writerName));
        }
        String memberName = Optional.ofNullable(evaluationSearchDTO.getMemberName()).orElse("");
        if (!memberName.isEmpty()) {
            evaluationSearchDTO.setMemberName(authQueryService.selectMemberIdByLoginId(memberName));
        }

        MemberDTO memberDTO = memberQueryService.selectMemberInfo(evaluationSearchDTO.getSearcherName());
        evaluationSearchDTO.setCenterId(memberDTO.getCenterId());

        List<EvaluationDTO> evaluationList = evaluationMapper.findEvaluationByCenterIdAndSearch(size,offset, evaluationSearchDTO, sortField, sortOrder);

        int total = evaluationMapper.findEvaluationByCenterIdAndSearchCount(evaluationSearchDTO);

        if(evaluationList.isEmpty() || total == 0){
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }
        evaluationList.forEach(evaluation -> {
            try {
                evaluation.setMemberId(memberQueryService.selectNameById(evaluation.getMemberId()));
                evaluation.setWriterId(memberQueryService.selectNameById(evaluation.getWriterId()));
                evaluation.setCenterId(centerQueryService.selectNameById(evaluation.getCenterId()));
            } catch (Exception e) {
                throw new EvaluationCommonException(EvaluationErrorCode.MEMBER_CENTER_NOT_FOUND);
            }
        });
        return new PageImpl<>(evaluationList, pageable, total);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> selectEvaluationBySearchByRepresentative(Pageable pageable, EvaluationSearchDTO evaluationSearchDTO){
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        String writerName = Optional.ofNullable(evaluationSearchDTO.getWriterName()).orElse("");
        if (!writerName.isEmpty()) {
            evaluationSearchDTO.setWriterName(authQueryService.selectMemberIdByLoginId(writerName));
        }
        String memberName = Optional.ofNullable(evaluationSearchDTO.getMemberName()).orElse("");
        if (!memberName.isEmpty()) {
            evaluationSearchDTO.setMemberName(authQueryService.selectMemberIdByLoginId(memberName));
        }

        List<EvaluationDTO> evaluationList = evaluationMapper.findEvaluationBySearch(size,offset, evaluationSearchDTO, sortField, sortOrder);

        int total = evaluationMapper.findEvaluationBySearchCount(evaluationSearchDTO);

        if(evaluationList.isEmpty() || total == 0) {
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }

        evaluationList.forEach(evaluation -> {
            try {
                evaluation.setMemberId(memberQueryService.selectNameById(evaluation.getMemberId()));
                evaluation.setWriterId(memberQueryService.selectNameById(evaluation.getWriterId()));
                evaluation.setCenterId(centerQueryService.selectNameById(evaluation.getCenterId()));
            } catch (Exception e) {
                throw new EvaluationCommonException(EvaluationErrorCode.MEMBER_CENTER_NOT_FOUND);
            }
        });

        return new PageImpl<>(evaluationList, pageable, total);
    }

    @Override
    @Transactional
    public void exportEvaluationToExcel(HttpServletResponse response) {
        List<EvaluationExcelDownload> evaluationList = evaluationMapper.findEvaluationForExcel();

        if(evaluationList == null) {
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }

        evaluationList.forEach(evaluation -> {
            try {
                evaluation.setMemberId(memberQueryService.selectNameById(evaluation.getMemberId()));
                evaluation.setWriterId(memberQueryService.selectNameById(evaluation.getWriterId()));
                evaluation.setCenterId(centerQueryService.selectNameById(evaluation.getCenterId()));
            } catch (Exception e) {
                throw new EvaluationCommonException(EvaluationErrorCode.MEMBER_CENTER_NOT_FOUND);
            }
        });

        excelUtilsV1.download(EvaluationExcelDownload.class, evaluationList, "EvaluationExcel", response);

    }
}
