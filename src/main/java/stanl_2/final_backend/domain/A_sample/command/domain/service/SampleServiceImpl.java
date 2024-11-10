package stanl_2.final_backend.domain.A_sample.command.domain.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.A_sample.command.application.dto.request.PostRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.request.PutRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.response.PutResponseDTO;
import stanl_2.final_backend.domain.A_sample.command.application.service.SampleService;
import stanl_2.final_backend.domain.A_sample.command.domain.aggregate.entity.Sample;
import stanl_2.final_backend.domain.A_sample.command.domain.repository.SampleRepository;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service("commandSampleService")
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService {

    private final stanl_2.final_backend.domain.A_sample.query.service.SampleService sampleService;
    private final SampleRepository sampleRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    // 다음 pk 값 생성 로직
    private String generateNextId() {
        String lastId = getLastIdFromDatabase();
        if (lastId == null) {
            return "SAM_000001";
        }
        int nextIdNum = Integer.parseInt(lastId.substring(4)) + 1;
        return String.format("SAM_%06d", nextIdNum);
    }

    // 이전 pk 값 확인
    private String getLastIdFromDatabase() {
        try {
            return (String) entityManager
                    .createQuery("SELECT s.id FROM Sample s ORDER BY s.id DESC")
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

    @Override
    @Transactional
    public void register(PostRequestDTO postRequestDTO) {

        String newId = generateNextId();
        Sample newSample = modelMapper.map(postRequestDTO, Sample.class);
        newSample.setId(newId);

        sampleRepository.save(newSample);
    }

    @Override
    @Transactional
    public PutResponseDTO modify(String id, PutRequestDTO putRequestDTO) {

        stanl_2.final_backend.domain.A_sample.query.dto.SampleDTO sampleDTO = sampleService.findById(id);

        Sample updateSample = modelMapper.map(sampleDTO, Sample.class);
        updateSample.setName(putRequestDTO.getName());

        Sample responseSample = sampleRepository.save(updateSample);

        return modelMapper.map(responseSample, PutResponseDTO.class);
    }

    @Override
    @Transactional
    public void remove(String id) {

        stanl_2.final_backend.domain.A_sample.query.dto.SampleDTO sampleDTO = sampleService.findById(id);

        Sample deleteSample = modelMapper.map(sampleDTO, Sample.class);
        deleteSample.setDeletedAt(getCurrentTimestamp());

        sampleRepository.save(deleteSample);
    }

}
