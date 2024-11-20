package stanl_2.final_backend.domain.organization.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.organization.command.application.service.OrganizationCommandService;
import stanl_2.final_backend.domain.organization.command.domain.repository.OrganizationRepository;

@Service("commandOrganizationService")
public class OrganizationCommandServiceImpl implements OrganizationCommandService {

    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrganizationCommandServiceImpl(OrganizationRepository organizationRepository,
                                          ModelMapper modelMapper) {
        this.organizationRepository = organizationRepository;
        this.modelMapper = modelMapper;
    }


}
