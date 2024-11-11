package stanl_2.final_backend.domain.center.command.application.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CenterRegistRequestDTO {

//    private Long id;
    private String name;
    private String address;
    private String phone;
    private Integer memberCount;
    private String operatingAt;
}
