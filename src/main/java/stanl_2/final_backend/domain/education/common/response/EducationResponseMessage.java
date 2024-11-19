package stanl_2.final_backend.domain.education.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EducationResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}