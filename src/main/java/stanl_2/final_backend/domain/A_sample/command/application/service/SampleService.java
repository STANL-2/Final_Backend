package stanl_2.final_backend.domain.A_sample.command.application.service;

import stanl_2.final_backend.domain.A_sample.command.application.dto.request.PostRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.request.PutRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.response.DeleteResponseDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.response.PostResponseDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.response.PutResponseDTO;

public interface SampleService {
    PostResponseDTO register(PostRequestDTO postRequestDTO);

    PutResponseDTO modify(String id, PutRequestDTO putRequestDTO);

    DeleteResponseDTO remove(String id);
}
