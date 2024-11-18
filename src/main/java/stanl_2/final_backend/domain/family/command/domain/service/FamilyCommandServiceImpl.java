package stanl_2.final_backend.domain.family.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.family.command.application.service.FamilyCommandService;
import stanl_2.final_backend.domain.family.command.domain.repository.FamilyRepository;
import stanl_2.final_backend.global.utils.AESUtils;

@Service("commandFamilyService")
public class FamilyCommandServiceImpl implements FamilyCommandService {

    private final FamilyRepository familyRepository;
    private final ModelMapper modelMapper;
    private final AESUtils aesUtils;

    @Autowired
    public FamilyCommandServiceImpl(FamilyRepository familyRepository,
                                    ModelMapper modelMapper,
                                    AESUtils aesUtils) {
        this.familyRepository = familyRepository;
        this.modelMapper = modelMapper;
        this.aesUtils = aesUtils;
    }
}
