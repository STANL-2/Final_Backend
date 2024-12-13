package stanl_2.final_backend.domain.center.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterModifyDTO;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterRegistDTO;
import stanl_2.final_backend.domain.center.command.application.service.CenterCommandService;
import stanl_2.final_backend.domain.center.command.domain.aggregate.entity.Center;
import stanl_2.final_backend.domain.center.command.domain.repository.CenterRepository;
import stanl_2.final_backend.domain.center.common.exception.CenterCommonException;
import stanl_2.final_backend.domain.center.common.exception.CenterErrorCode;
import stanl_2.final_backend.domain.s3.command.application.service.S3FileService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("commandCenterServiceImpl")
public class CenterCommandServiceImpl implements CenterCommandService {

    private final CenterRepository centerRepository;
    private final ModelMapper modelMapper;
    private final S3FileService s3FileService;

    @Autowired
    public CenterCommandServiceImpl(CenterRepository centerRepository, ModelMapper modelMapper, S3FileService s3FileService) {
        this.centerRepository = centerRepository;
        this.modelMapper = modelMapper;
        this.s3FileService = s3FileService;
    }


    @Override
    @Transactional
    public void registCenter(CenterRegistDTO centerRegistDTO, MultipartFile imageUrl) {

        Center newCenter = modelMapper.map(centerRegistDTO, Center.class);

        newCenter.setImageUrl(s3FileService.uploadOneFile(imageUrl));

        centerRepository.save(newCenter);
    }

    @Override
    @Transactional
    public void modifyCenter(CenterModifyDTO centerModifyDTO, MultipartFile imageUrl) {
        Center center = centerRepository.findById(centerModifyDTO.getCenterId())
                .orElseThrow(() -> new CenterCommonException(CenterErrorCode.CENTER_NOT_FOUND));

        s3FileService.deleteFile(center.getImageUrl());

        Center updateCenter = modelMapper.map(centerModifyDTO, Center.class);

        updateCenter.setImageUrl(s3FileService.uploadOneFile(imageUrl));

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
