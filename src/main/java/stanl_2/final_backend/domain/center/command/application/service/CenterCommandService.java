package stanl_2.final_backend.domain.center.command.application.service;

import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterModifyDTO;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterRegistDTO;

@Service
public interface CenterCommandService {

    void registCenter(CenterRegistDTO centerRegistDTO);
    void modifyCenter(CenterModifyDTO centerModifyDTO);
    void deleteCenter(String id);
}
