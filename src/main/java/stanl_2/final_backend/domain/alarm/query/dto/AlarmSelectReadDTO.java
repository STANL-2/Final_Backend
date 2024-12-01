package stanl_2.final_backend.domain.alarm.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlarmSelectReadDTO {

    private String message;
    private String type;
    private String tag;
    private String redirectUrl;
    private Boolean readStatus;

    private String memberLoginId;
}
