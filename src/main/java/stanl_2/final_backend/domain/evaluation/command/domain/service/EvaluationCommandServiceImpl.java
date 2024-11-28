package stanl_2.final_backend.domain.evaluation.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.evaluation.command.application.dto.EvaluationModifyDTO;
import stanl_2.final_backend.domain.evaluation.command.application.dto.EvaluationRegistDTO;
import stanl_2.final_backend.domain.evaluation.command.application.service.EvaluationCommandService;
import stanl_2.final_backend.domain.evaluation.command.domain.aggregate.entity.Evaluation;
import stanl_2.final_backend.domain.evaluation.command.domain.repository.EvaluationRepository;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationCommonException;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationErrorCode;
import stanl_2.final_backend.domain.s3.command.application.service.S3FileService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("commandEvaluationService")
public class EvaluationCommandServiceImpl implements EvaluationCommandService {

    private final EvaluationRepository evaluationRepository;
    private final ModelMapper modelMapper;
    private final S3FileService s3FileService;

    @Autowired
    public EvaluationCommandServiceImpl(EvaluationRepository evaluationRepository, ModelMapper modelMapper, S3FileService s3FileService) {
        this.evaluationRepository = evaluationRepository;
        this.modelMapper = modelMapper;
        this.s3FileService = s3FileService;
    }

    private String getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void registerEvaluation(EvaluationRegistDTO evaluationRegistRequestDTO, MultipartFile fileUrl) {

        Evaluation evaluation = modelMapper.map(evaluationRegistRequestDTO, Evaluation.class);

        evaluation.setFileUrl(s3FileService.uploadOneFile(fileUrl));

        evaluationRepository.save(evaluation);
    }

    @Override
    @Transactional
    public void modifyEvaluation(EvaluationModifyDTO evaluationModifyRequestDTO, MultipartFile fileUrl) {
        Evaluation evaluation = evaluationRepository.findById(evaluationModifyRequestDTO.getEvaluationId())
                .orElseThrow(() -> new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND));

        s3FileService.deleteFile(evaluation.getFileUrl());

        Evaluation updateEvaluation = modelMapper.map(evaluationModifyRequestDTO, Evaluation.class);

        updateEvaluation.setFileUrl(s3FileService.uploadOneFile(fileUrl));

        updateEvaluation.setEvaluationId(evaluation.getEvaluationId());
        updateEvaluation.setCreatedAt(evaluation.getCreatedAt());
        updateEvaluation.setUpdatedAt(getCurrentTime());
        updateEvaluation.setActive(evaluation.getActive());

        evaluationRepository.save(updateEvaluation);
    }

    @Override
    @Transactional
    public void deleteEvaluation(String id) {
        Evaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND));

        evaluation.setActive(false);
        evaluation.setDeletedAt(getCurrentTime());

        evaluationRepository.save(evaluation);
    }
}
