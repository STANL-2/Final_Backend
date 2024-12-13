package stanl_2.final_backend.domain.education.command.domain.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.education.command.application.service.EducationCommandService;
import stanl_2.final_backend.domain.education.command.domain.repository.EducationRepository;

@Service("commandEducationService")
public class EducationCommandServiceImpl implements EducationCommandService {

    private final EducationRepository educationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EducationCommandServiceImpl(EducationRepository educationRepository,
                                       ModelMapper modelMapper) {
        this.educationRepository = educationRepository;
        this.modelMapper = modelMapper;
    }
}
