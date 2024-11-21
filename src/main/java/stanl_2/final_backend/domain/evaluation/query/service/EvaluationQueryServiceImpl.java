package stanl_2.final_backend.domain.evaluation.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationCommonException;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationErrorCode;
import stanl_2.final_backend.domain.evaluation.common.util.EvaluationRequestList;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationSearchDTO;
import stanl_2.final_backend.domain.evaluation.query.repository.EvaluationMapper;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EvaluationQueryServiceImpl implements EvaluationQueryService {

    private final EvaluationMapper evaluationMapper;
    private final MemberQueryService memberQueryService;
    private final AuthQueryService authQueryService;

    @Autowired
    public EvaluationQueryServiceImpl(EvaluationMapper evaluationMapper, MemberQueryService memberQueryService, AuthQueryService authQueryService) {
        this.evaluationMapper = evaluationMapper;
        this.memberQueryService = memberQueryService;
        this.authQueryService = authQueryService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> selectAllEvaluationsByManager(EvaluationDTO evaluationDTO, Pageable pageable) throws GeneralSecurityException {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        MemberDTO memberDTO = memberQueryService.selectMemberInfo(evaluationDTO.getMemberId());
        String centerId = memberDTO.getCenterId();

        List<EvaluationDTO> evaluationList = evaluationMapper.findEvaluationByCenterId(size,offset, centerId);

        int total = evaluationMapper.findEvaluationCountByCenterId(centerId);

        if(evaluationList.isEmpty() || total == 0) {
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }

        /* 설명. ID를 LOGINID로 바꿔주는 메소드 찾아서... */
//            evaluationList.forEach(evaluation -> {
//                try {
//                    evaluation.setMemberId(evaluation.getMemberName()));
//                } catch (GeneralSecurityException e) {
//                    throw new RuntimeException(e);
//                }
//            });
        return new PageImpl<>(evaluationList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> selectAllEvaluationsByRepresentative(EvaluationDTO evaluationDTO, Pageable pageable) throws GeneralSecurityException {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<EvaluationDTO> evaluationList = evaluationMapper.findAllEvaluations(size,offset);

        int total = evaluationMapper.findEvaluationCount();

        if(evaluationList.isEmpty() || total == 0) {
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }

        /* 설명. ID를 LOGINID로 바꿔주는 메소드 찾아서... */
//            evaluationList.forEach(evaluation -> {
//                try {
//                    evaluation.setMemberId(evaluation.getMemberName()));
//                } catch (GeneralSecurityException e) {
//                    throw new RuntimeException(e);
//                }
//            });
        return new PageImpl<>(evaluationList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public EvaluationDTO selectEvaluationById(String id) {

        EvaluationDTO evaluationDTO = evaluationMapper.findEvaluationById(id);

        if(evaluationDTO == null){
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }
        return evaluationDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> selectEvaluationBySearchByManager(Pageable pageable, EvaluationSearchDTO evaluationSearchDTO) throws GeneralSecurityException {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

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

        List<EvaluationDTO> evaluationList = evaluationMapper.findEvaluationByCenterIdAndSearch(size,offset, evaluationSearchDTO);

        int total = evaluationMapper.findEvaluationByCenterIdAndSearchCount(evaluationSearchDTO);

        if(evaluationList.isEmpty() || total == 0){
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }
            /* 설명. ID를 LOGINID로 바꿔주는 메소드 찾아서... */
//            evaluationList.forEach(evaluation -> {
//                try {
//                    evaluation.setMemberId(evaluation.getMemberName()));
//                } catch (GeneralSecurityException e) {
//                    throw new RuntimeException(e);
//                }
//            });
        return new PageImpl<>(evaluationList, pageable, total);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> selectEvaluationBySearchByRepresentative(Pageable pageable, EvaluationSearchDTO evaluationSearchDTO) throws GeneralSecurityException {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        String writerName = Optional.ofNullable(evaluationSearchDTO.getWriterName()).orElse("");
        if (!writerName.isEmpty()) {
            evaluationSearchDTO.setWriterName(authQueryService.selectMemberIdByLoginId(writerName));
        }
        String memberName = Optional.ofNullable(evaluationSearchDTO.getMemberName()).orElse("");
        if (!memberName.isEmpty()) {
            evaluationSearchDTO.setMemberName(authQueryService.selectMemberIdByLoginId(memberName));
        }

        List<EvaluationDTO> evaluationList = evaluationMapper.findEvaluationBySearch(size,offset, evaluationSearchDTO);

        int total = evaluationMapper.findEvaluationBySearchCount(evaluationSearchDTO);

        if(evaluationList.isEmpty() || total == 0) {
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }

        return new PageImpl<>(evaluationList, pageable, total);

    }
}
