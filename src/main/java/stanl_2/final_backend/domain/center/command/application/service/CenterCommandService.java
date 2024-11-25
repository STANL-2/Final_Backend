package stanl_2.final_backend.domain.center.command.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterModifyDTO;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterRegistDTO;

@Service
public interface CenterCommandService {

    void registCenter(CenterRegistDTO centerRegistDTO, MultipartFile imageUrl);
    void modifyCenter(CenterModifyDTO centerModifyDTO, MultipartFile imageUrl);
    void deleteCenter(String id);
}
