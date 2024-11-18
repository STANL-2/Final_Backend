package stanl_2.final_backend.domain.certification.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.certification.command.application.dto.CertificationRegisterDTO;
import stanl_2.final_backend.domain.certification.command.application.service.CertificationCommandService;
import stanl_2.final_backend.domain.certification.command.domain.aggregate.entity.Certification;
import stanl_2.final_backend.domain.certification.command.domain.repository.CertificationRepository;

@Service("commandCertificationService")
public class CertificationCommandServiceImpl implements CertificationCommandService {

    private final CertificationRepository certificationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CertificationCommandServiceImpl(CertificationRepository certificationRepository,
                                           ModelMapper modelMapper) {
        this.certificationRepository = certificationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void registCertification(CertificationRegisterDTO certificationRegisterDTO) {

        Certification certification = modelMapper.map(certificationRegisterDTO, Certification.class);

        certificationRepository.save(certification);
    }
}
