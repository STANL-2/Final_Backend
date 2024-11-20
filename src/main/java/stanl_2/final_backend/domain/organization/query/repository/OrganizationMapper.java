package stanl_2.final_backend.domain.organization.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.organization.query.dto.OrganizationDTO;

import java.util.List;

@Mapper
public interface OrganizationMapper {
    List<OrganizationDTO> selectOrganizationChart();
}
