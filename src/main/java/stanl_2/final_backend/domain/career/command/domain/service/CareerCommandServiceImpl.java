package stanl_2.final_backend.domain.career.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.career.command.application.service.CareerCommandService;
import stanl_2.final_backend.domain.career.command.domain.repository.CareerRepository;

@Service("commandCareerService")
public class CareerCommandServiceImpl implements CareerCommandService {

    private final CareerRepository careerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CareerCommandServiceImpl(CareerRepository careerRepository,
                                    ModelMapper modelMapper) {
        this.careerRepository = careerRepository;
        this.modelMapper = modelMapper;
    }
}
