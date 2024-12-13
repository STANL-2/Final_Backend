package stanl_2.final_backend.domain.organization.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrganizationRegisterDTO {
    private String name;
    private String parent;
}
