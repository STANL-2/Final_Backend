package stanl_2.final_backend.domain.A_sample.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleRegistDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleModifyDTO;
import stanl_2.final_backend.domain.A_sample.command.application.service.SampleCommandService;
import stanl_2.final_backend.domain.A_sample.command.domain.aggregate.entity.Sample;
import stanl_2.final_backend.domain.A_sample.command.domain.repository.SampleRepository;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleCommonException;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleErrorCode;
import stanl_2.final_backend.domain.s3.command.application.service.S3FileService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("commandSampleService")
public class SampleCommandServiceImpl implements SampleCommandService {

    private final SampleRepository sampleRepository;
    private final ModelMapper modelMapper;
    private final S3FileService s3FileService;

    @Autowired
    public SampleCommandServiceImpl(SampleRepository sampleRepository, ModelMapper modelMapper, S3FileService s3FileService) {
        this.sampleRepository = sampleRepository;
        this.modelMapper = modelMapper;
        this.s3FileService = s3FileService;
    }

    private String getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void registerSample(SampleRegistDTO sampleRegistRequestDTO) {

        Sample newSample = modelMapper.map(sampleRegistRequestDTO, Sample.class);

        sampleRepository.save(newSample);
    }

    @Override
    public void registerSampleFile(SampleRegistDTO sampleRegistRequestDTO, MultipartFile imageUrl) {
        Sample newSample = modelMapper.map(sampleRegistRequestDTO, Sample.class);


        // s3 사용
        newSample.setImageUrl(s3FileService.uploadOneFile(imageUrl));

        sampleRepository.save(newSample);
    }

    @Override
    @Transactional
    public SampleModifyDTO modifySample(String id, SampleModifyDTO sampleModifyRequestDTO) {

        Sample sample = sampleRepository.findById(id)
                .orElseThrow(() -> new SampleCommonException(SampleErrorCode.SAMPLE_NOT_FOUND));

        sampleModifyRequestDTO.setId(id);
        Sample updateSample = modelMapper.map(sampleModifyRequestDTO, Sample.class);
        updateSample.setCreatedAt(sample.getCreatedAt());
        updateSample.setActive(sample.getActive());

        sampleRepository.save(updateSample);

        SampleModifyDTO sampleModifyResponseDTO= modelMapper.map(updateSample, SampleModifyDTO.class);

        return sampleModifyResponseDTO;
    }

    @Override
    @Transactional
    public void deleteSample(String id) {

        Sample sample = sampleRepository.findById(id)
                .orElseThrow(() -> new SampleCommonException(SampleErrorCode.SAMPLE_NOT_FOUND));

        sample.setActive(false);
        sample.setDeletedAt(getCurrentTimestamp());

        sampleRepository.save(sample);
    }

}
