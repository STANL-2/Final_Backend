package stanl_2.final_backend.domain.A_sample.command.domain.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.A_sample.command.application.dto.request.PostRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.service.SampleService;
import stanl_2.final_backend.domain.A_sample.command.domain.aggregate.Sample;
import stanl_2.final_backend.domain.A_sample.command.domain.repository.SampleRepository;

@Service("commandSampleService")
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService {

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

    @Override
    @Transactional
    public void register(PostRequestDTO postRequestDTO) {

        String newId = generateNextId();
        Sample newSample = modelMapper.map(postRequestDTO, Sample.class);
        newSample.setId(newId);

        sampleRepository.save(newSample);
    }
}
