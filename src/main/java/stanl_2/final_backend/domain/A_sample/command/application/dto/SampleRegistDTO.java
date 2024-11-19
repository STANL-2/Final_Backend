package stanl_2.final_backend.domain.A_sample.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class SampleRegistDTO {
    private String id;
    private String name;
    private Integer num;
}
