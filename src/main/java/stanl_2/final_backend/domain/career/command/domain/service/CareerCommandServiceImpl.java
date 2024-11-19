package stanl_2.final_backend.domain.career.command.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.career.command.application.dto.CareerRegistDTO;
import stanl_2.final_backend.domain.career.command.application.service.CareerCommandService;
import stanl_2.final_backend.domain.career.command.domain.aggregate.entity.Career;
import stanl_2.final_backend.domain.career.command.domain.repository.CareerRepository;

@Slf4j
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

    @Override
    @Transactional
    public void registCareer(CareerRegistDTO careerRegistDTO) {

        Career career = modelMapper.map(careerRegistDTO, Career.class);

        careerRepository.save(career);
    }
}
