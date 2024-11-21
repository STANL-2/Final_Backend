package stanl_2.final_backend.domain.organization.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.organization.query.dto.OrganizationDTO;
import stanl_2.final_backend.domain.organization.query.repository.OrganizationMapper;

import java.util.List;

@Service("queryOrganizationService")
public class OrganizationQueryServiceImpl implements OrganizationQueryService{

    private final OrganizationMapper organizationMapper;

    @Autowired
    public OrganizationQueryServiceImpl(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationDTO> selectOrganizationChart() {

        List<OrganizationDTO> organizationDTO = organizationMapper.selectOrganizationChart();

        return organizationDTO;
    }
}
