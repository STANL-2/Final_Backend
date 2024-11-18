package stanl_2.final_backend.domain.certification.command.application.service;

import stanl_2.final_backend.domain.certification.command.application.dto.CertificationRegisterDTO;

public interface CertificationCommandService {
    void registCertification(CertificationRegisterDTO certificationRegisterDTO);
}
