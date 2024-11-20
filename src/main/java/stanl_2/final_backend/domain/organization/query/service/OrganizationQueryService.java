package stanl_2.final_backend.domain.organization.query.service;

import stanl_2.final_backend.domain.organization.query.dto.OrganizationDTO;

import java.util.List;

public interface OrganizationQueryService {
    List<OrganizationDTO> selectOrganizationChart();

}
