package stanl_2.final_backend.domain.alarm.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlarmSelectDTO {

    private String message;
    private String type;
    private String redirectUrl;
    private Boolean readStatus;

    private String memberLoginId;
}
