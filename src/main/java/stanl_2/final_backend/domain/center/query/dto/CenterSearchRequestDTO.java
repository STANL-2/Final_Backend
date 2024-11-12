package stanl_2.final_backend.domain.center.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CenterSearchRequestDTO {
    private String id;
    private String name;
    private String address;
}
