package stanl_2.final_backend.domain.center.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterModifyRequestDTO;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterRegistRequestDTO;
import stanl_2.final_backend.domain.center.command.application.service.CenterCommandService;
import stanl_2.final_backend.domain.center.command.domain.aggregate.entity.Center;
import stanl_2.final_backend.domain.center.command.domain.repository.CenterRepository;
import stanl_2.final_backend.domain.center.common.exception.CenterCommonException;
import stanl_2.final_backend.domain.center.common.exception.CenterErrorCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("commandCenterServiceImpl")
public class CenterCommandServiceImpl implements CenterCommandService {

    private final CenterRepository centerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CenterCommandServiceImpl(CenterRepository centerRepository, ModelMapper modelMapper) {
        this.centerRepository = centerRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public void registCenter(CenterRegistRequestDTO centerRegistRequestDTO) {

        Center newCenter = modelMapper.map(centerRegistRequestDTO, Center.class);

        centerRepository.save(newCenter);
    }

    @Override
    @Transactional
    public void modifyCenter(CenterModifyRequestDTO centerModifyRequestDTO) {
        Center center = centerRepository.findById(centerModifyRequestDTO.getCenterId())
                .orElseThrow(() -> new CenterCommonException(CenterErrorCode.CENTER_NOT_FOUND));

        Center updateCenter = modelMapper.map(centerModifyRequestDTO, Center.class);

        updateCenter.setCenterId(center.getCenterId());
        updateCenter.setCreatedAt(center.getCreatedAt());
        updateCenter.setUpdatedAt(getCurrentTime());
        updateCenter.setActive(center.getActive());

        centerRepository.save(updateCenter);
    }

    @Override
    @Transactional
    public void deleteCenter(String id) {
        Center center = centerRepository.findById(id)
                .orElseThrow(() -> new CenterCommonException(CenterErrorCode.CENTER_NOT_FOUND));

        center.setActive(false);
        center.setDeletedAt(getCurrentTime());

        centerRepository.save(center);
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
