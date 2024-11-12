package stanl_2.final_backend.domain.center.command.application.service;

import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterModifyRequestDTO;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterRegistRequestDTO;

@Service
public interface CenterCommandService {

    void registCenter(CenterRegistRequestDTO centerRegistRequestDTO);
    void modifyCenter(String id, CenterModifyRequestDTO centerModifyRequestDTO);
    void deleteCenter(String id);
}
