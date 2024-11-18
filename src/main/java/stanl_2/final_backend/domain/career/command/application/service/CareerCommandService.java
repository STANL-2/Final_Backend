package stanl_2.final_backend.domain.career.command.application.service;

import stanl_2.final_backend.domain.career.command.application.dto.CareerRegistDTO;

public interface CareerCommandService {
    void registCareer(CareerRegistDTO careerRegistDTO);

}
