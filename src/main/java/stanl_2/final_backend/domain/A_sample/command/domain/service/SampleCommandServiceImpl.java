package stanl_2.final_backend.domain.A_sample.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.A_sample.command.application.dto.request.SampleRegistRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.request.SampleModifyRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.response.SampleModifyResponseDTO;
import stanl_2.final_backend.domain.A_sample.command.application.service.SampleCommandService;
import stanl_2.final_backend.domain.A_sample.command.domain.aggregate.entity.Sample;
import stanl_2.final_backend.domain.A_sample.command.domain.repository.SampleRepository;
import stanl_2.final_backend.domain.A_sample.common.exception.CommonException;
import stanl_2.final_backend.domain.A_sample.common.exception.ErrorCode;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleDTO;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service("commandSampleService")
public class SampleCommandServiceImpl implements SampleCommandService {

    private final SampleRepository sampleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SampleCommandServiceImpl(SampleRepository sampleRepository, ModelMapper modelMapper) {
        this.sampleRepository = sampleRepository;
        this.modelMapper = modelMapper;
    }

    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

    @Override
    @Transactional
    public void registerSample(SampleRegistRequestDTO sampleRegistRequestDTO) {

        Sample newSample = modelMapper.map(sampleRegistRequestDTO, Sample.class);

        sampleRepository.save(newSample);
    }

    @Override
    @Transactional
    public SampleModifyResponseDTO modifySample(String id, SampleModifyRequestDTO sampleModifyRequestDTO) {

        Sample sample = sampleRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.SAMPLE_NOT_FOUND));

        sampleModifyRequestDTO.setId(id);
        Sample updateSample = modelMapper.map(sampleModifyRequestDTO, Sample.class);
        updateSample.setCreatedAt(sample.getCreatedAt());
        updateSample.setActive(sample.getActive());

        sampleRepository.save(updateSample);

        SampleModifyResponseDTO sampleModifyResponseDTO= modelMapper.map(updateSample, SampleModifyResponseDTO.class);

        return sampleModifyResponseDTO;
    }

    @Override
    @Transactional
    public void deleteSample(String id) {

        Sample sample = sampleRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.SAMPLE_NOT_FOUND));

        sample.setActive(false);
        sample.setDeletedAt(getCurrentTimestamp());

        sampleRepository.save(sample);
    }
}
