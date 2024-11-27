package stanl_2.final_backend.domain.organization.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrganizationDTO {
    private String organizationId;
    private String name;
    private String parent;
}
