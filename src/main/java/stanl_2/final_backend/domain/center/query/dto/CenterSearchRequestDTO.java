package stanl_2.final_backend.domain.center.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CenterSearchRequestDTO {
    private String centerId;
    private String name;
    private String address;
}
