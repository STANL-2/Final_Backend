package stanl_2.final_backend.domain.center.command.application.service;

import stanl_2.final_backend.domain.center.command.application.dto.request.CenterRegistRequestDTO;
import stanl_2.final_backend.domain.center.command.application.dto.response.CenterRegistResponseDTO;

public interface CenterService {

    CenterRegistResponseDTO registCenter(CenterRegistRequestDTO centerRegistRequestDTO);

}
