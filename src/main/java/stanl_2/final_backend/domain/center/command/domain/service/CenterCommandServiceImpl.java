package stanl_2.final_backend.domain.center.command.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.A_sample.common.exception.CommonException;
import stanl_2.final_backend.domain.A_sample.common.exception.ErrorCode;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterModifyRequestDTO;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterRegistRequestDTO;
import stanl_2.final_backend.domain.center.command.application.dto.response.CenterModifyResponseDTO;
import stanl_2.final_backend.domain.center.command.application.service.CenterCommandService;
import stanl_2.final_backend.domain.center.command.domain.aggregate.entity.Center;
import stanl_2.final_backend.domain.center.command.domain.repository.CenterRepository;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service("commandCenterServiceImpl")
public class CenterCommandServiceImpl implements CenterCommandService {

    private final CenterRepository centerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CenterCommandServiceImpl(CenterRepository centerRepository, ModelMapper modelMapper) {
        this.centerRepository = centerRepository;
        this.modelMapper = modelMapper;
    }

    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

    @Override
    @Transactional
    public void registCenter(CenterRegistRequestDTO centerRegistRequestDTO) {

        Center newCenter = modelMapper.map(centerRegistRequestDTO, Center.class);

        centerRepository.save(newCenter);
    }

    @Override
    @Transactional
    public void modifyCenter(String id, CenterModifyRequestDTO centerModifyRequestDTO) {
        Center center = centerRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.CENTER_NOT_FOUND));

//        centerModifyRequestDTO.setId(id);
        Center updateCenter = modelMapper.map(centerModifyRequestDTO, Center.class);
        updateCenter.setCreatedAt(center.getCreatedAt());
        updateCenter.setActive(center.getActive());

        centerRepository.save(updateCenter);

        CenterModifyResponseDTO centerModifyResponseDTO= modelMapper.map(updateCenter, CenterModifyResponseDTO.class);

    }

    @Override
    @Transactional
    public void deleteCenter(String id) {

        Center center = centerRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.CENTER_NOT_FOUND));

        center.setActive(false);
        center.setDeletedAt(getCurrentTimestamp());

        centerRepository.save(center);
    }
}
